package com.bank.dcp.monitoring.measurement.service;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.PageableValidator;
import com.bank.dcp.monitoring.common.exception.InvalidRequestException;
import com.bank.dcp.monitoring.common.exception.ResourceNotFoundException;
import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import com.bank.dcp.monitoring.measurement.dto.EnergyMeasurementRequest;
import com.bank.dcp.monitoring.measurement.dto.EnergyMeasurementResponse;
import com.bank.dcp.monitoring.measurement.dto.MeasurementBulkRequest;
import com.bank.dcp.monitoring.measurement.dto.MeasurementBulkResponse;
import com.bank.dcp.monitoring.measurement.dto.MeasurementFilter;
import com.bank.dcp.monitoring.measurement.dto.MeasurementSeriesPointResponse;
import com.bank.dcp.monitoring.measurement.entity.EnergyMeasurement;
import com.bank.dcp.monitoring.measurement.mapper.EnergyMeasurementMapper;
import com.bank.dcp.monitoring.measurement.repository.EnergyMeasurementRepository;
import com.bank.dcp.monitoring.measurement.specification.MeasurementSpecifications;
import com.bank.dcp.monitoring.server.entity.Server;
import com.bank.dcp.monitoring.server.repository.ServerRepository;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import com.bank.dcp.monitoring.source.repository.MeasurementSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeasurementService {
    private final EnergyMeasurementRepository measurementRepository;
    private final ServerRepository serverRepository;
    private final MeasurementSourceRepository sourceRepository;
    private final EnergyMeasurementMapper measurementMapper;

    public Page<EnergyMeasurementResponse> list(MeasurementFilter filter, Pageable pageable) {
        validateFilter(filter);
        Pageable safePageable = PageableValidator.validate(
                pageable,
                Constants.SortFields.MEASURED_AT,
                Constants.SortFields.MEASURED_AT,
                Constants.SortFields.POWER_W,
                Constants.SortFields.ENERGY_INTERVAL_KWH,
                Constants.SortFields.CPU_LOAD_PCT,
                Constants.SortFields.QUALITY_STATUS,
                Constants.SortFields.CREATED_AT
        );

        return measurementRepository.findAll(MeasurementSpecifications.byFilter(filter), safePageable)
                .map(measurementMapper::toResponse);
    }

    public Page<MeasurementSeriesPointResponse> series(MeasurementFilter filter, Pageable pageable) {
        validateFilter(filter);
        Pageable safePageable = PageableValidator.validate(
                pageable,
                Constants.SortFields.MEASURED_AT,
                Constants.SortFields.MEASURED_AT,
                Constants.SortFields.POWER_W,
                Constants.SortFields.ENERGY_INTERVAL_KWH,
                Constants.SortFields.CPU_LOAD_PCT,
                Constants.SortFields.QUALITY_STATUS
        );
        Pageable chronologicalPage = PageRequest.of(
                safePageable.getPageNumber(),
                safePageable.getPageSize(),
                Sort.by(Sort.Direction.ASC, Constants.SortFields.MEASURED_AT)
        );

        return measurementRepository.findAll(MeasurementSpecifications.byFilter(filter), chronologicalPage)
                .map(measurementMapper::toSeriesPoint);
    }

    public EnergyMeasurementResponse getById(UUID id) {
        EnergyMeasurement measurement = measurementRepository.getEnergyMeasurementById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ResourceNames.MEASUREMENT, id));

        return measurementMapper.toResponse(measurement);
    }

    @Transactional
    public EnergyMeasurementResponse create(EnergyMeasurementRequest request) {
        Server server = getServerById(request.serverId());
        MeasurementSource source = getSourceById(request.sourceId());
        EnergyMeasurement measurement = measurementMapper.toEntity(request, server, source);
        EnergyMeasurement savedMeasurement = measurementRepository.saveAndFlush(measurement);

        return measurementMapper.toResponse(savedMeasurement);
    }

    @Transactional
    public MeasurementBulkResponse createBulk(MeasurementBulkRequest request) {
        List<EnergyMeasurementRequest> measurementRequests = request.measurements();
        Map<UUID, Server> serversById = serverRepository.findAllById(
                        measurementRequests.stream()
                                .map(EnergyMeasurementRequest::serverId)
                                .collect(Collectors.toSet())
                )
                .stream()
                .collect(Collectors.toMap(Server::getId, Function.identity()));
        Map<UUID, MeasurementSource> sourcesById = sourceRepository.findAllById(
                        measurementRequests.stream()
                                .map(EnergyMeasurementRequest::sourceId)
                                .collect(Collectors.toSet())
                )
                .stream()
                .collect(Collectors.toMap(MeasurementSource::getId, Function.identity()));

        List<EnergyMeasurement> measurements = measurementRequests.stream()
                .map(measurementRequest -> toEntity(measurementRequest, serversById, sourcesById))
                .toList();
        List<EnergyMeasurement> savedMeasurements = measurementRepository.saveAllAndFlush(measurements);
        List<UUID> measurementIds = savedMeasurements.stream()
                .map(EnergyMeasurement::getId)
                .toList();

        return MeasurementBulkResponse.builder()
                .receivedCount(measurementRequests.size())
                .savedCount(savedMeasurements.size())
                .measurementIds(measurementIds)
                .build();
    }

    private EnergyMeasurement toEntity(
            EnergyMeasurementRequest request,
            Map<UUID, Server> serversById,
            Map<UUID, MeasurementSource> sourcesById
    ) {
        Server server = serversById.get(request.serverId());
        if (Objects.isNull(server)) {
            throw new ResourceNotFoundException(Constants.ResourceNames.SERVER, request.serverId());
        }

        MeasurementSource source = sourcesById.get(request.sourceId());
        if (Objects.isNull(source)) {
            throw new ResourceNotFoundException(Constants.ResourceNames.SOURCE, request.sourceId());
        }

        return measurementMapper.toEntity(request, server, source);
    }

    private Server getServerById(UUID id) {
        return serverRepository.getServerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ResourceNames.SERVER, id));
    }

    private MeasurementSource getSourceById(UUID id) {
        return sourceRepository.getMeasurementSourceById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ResourceNames.SOURCE, id));
    }

    private void validateFilter(MeasurementFilter filter) {
        if (Objects.nonNull(filter.from()) && Objects.nonNull(filter.to()) && filter.from().isAfter(filter.to())) {
            throw new InvalidRequestException(Constants.Messages.INVALID_TIME_RANGE);
        }

        BigDecimal minimumPower = filter.minPower();
        BigDecimal maximumPower = filter.maxPower();
        if (Objects.nonNull(minimumPower)
                && Objects.nonNull(maximumPower)
                && minimumPower.compareTo(maximumPower) > 0) {
            throw new InvalidRequestException(Constants.Messages.INVALID_POWER_RANGE);
        }
    }
}
