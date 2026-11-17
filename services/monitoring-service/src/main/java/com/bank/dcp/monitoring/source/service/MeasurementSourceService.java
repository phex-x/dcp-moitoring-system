package com.bank.dcp.monitoring.source.service;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.PageableValidator;
import com.bank.dcp.monitoring.common.enums.MeasurementSourceStatus;
import com.bank.dcp.monitoring.common.enums.MeasurementSourceType;
import com.bank.dcp.monitoring.common.exception.BusinessRuleViolationException;
import com.bank.dcp.monitoring.common.exception.ResourceNotFoundException;
import com.bank.dcp.monitoring.collector.dto.CollectorSyncRequest;
import com.bank.dcp.monitoring.collector.dto.CollectorJobResponse;
import com.bank.dcp.monitoring.collector.entity.CollectorJob;
import com.bank.dcp.monitoring.collector.mapper.CollectorJobMapper;
import com.bank.dcp.monitoring.collector.repository.CollectorJobRepository;
import com.bank.dcp.monitoring.collector.TelemetryCollector;
import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import com.bank.dcp.monitoring.datacenter.repository.DataCenterRepository;
import com.bank.dcp.monitoring.measurement.entity.EnergyMeasurement;
import com.bank.dcp.monitoring.measurement.repository.EnergyMeasurementRepository;
import com.bank.dcp.monitoring.source.dto.MeasurementSourcePatchRequest;
import com.bank.dcp.monitoring.source.dto.MeasurementSourceRequest;
import com.bank.dcp.monitoring.source.dto.MeasurementSourceResponse;
import com.bank.dcp.monitoring.source.dto.SourceAvailabilityResponse;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import com.bank.dcp.monitoring.source.mapper.MeasurementSourceMapper;
import com.bank.dcp.monitoring.source.repository.MeasurementSourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MeasurementSourceService {
    private static final int DEFAULT_SAMPLES_PER_SERVER = Constants.Validation.DEFAULT_SAMPLES_PER_SERVER;

    private final MeasurementSourceRepository sourceRepository;
    private final DataCenterRepository dataCenterRepository;
    private final CollectorJobRepository jobRepository;
    private final EnergyMeasurementRepository measurementRepository;
    private final MeasurementSourceMapper sourceMapper;
    private final CollectorJobMapper jobMapper;
    private final TelemetryCollector telemetryCollector;

    public Page<MeasurementSourceResponse> list(Pageable pageable) {
        Pageable safePageable = PageableValidator.validate(
                pageable,
                Constants.SortFields.NAME,
                Constants.SortFields.NAME,
                Constants.SortFields.TYPE,
                Constants.SortFields.STATUS,
                Constants.SortFields.POLL_INTERVAL_SEC
        );

        return sourceRepository.findAll(safePageable).map(sourceMapper::toResponse);
    }

    public MeasurementSourceResponse getById(UUID id) {
        return sourceMapper.toResponse(getSourceById(id));
    }

    @Transactional
    public MeasurementSourceResponse create(MeasurementSourceRequest request) {
        DataCenter dataCenter = getDataCenterById(request.dataCenterId());
        MeasurementSource source = sourceMapper.toEntity(request, dataCenter);
        MeasurementSource savedSource = sourceRepository.saveAndFlush(source);

        return sourceMapper.toResponse(savedSource);
    }

    @Transactional
    public MeasurementSourceResponse patch(UUID id, MeasurementSourcePatchRequest request) {
        MeasurementSource source = getSourceById(id);
        if (Objects.nonNull(request.status()) && !source.getStatus().canTransitionTo(request.status())) {
            throw new BusinessRuleViolationException(Constants.Messages.INVALID_SOURCE_STATUS_TRANSITION);
        }
        DataCenter dataCenter = Objects.isNull(request.dataCenterId())
                ? null
                : getDataCenterById(request.dataCenterId());
        sourceMapper.patch(request, dataCenter, source);

        MeasurementSource savedSource = sourceRepository.saveAndFlush(source);
        return sourceMapper.toResponse(savedSource);
    }

    @Transactional
    public void deactivate(UUID id) {
        MeasurementSource source = getSourceById(id);
        if (!source.getStatus().canTransitionTo(MeasurementSourceStatus.INACTIVE)) {
            throw new BusinessRuleViolationException(Constants.Messages.SOURCE_CANNOT_BE_INACTIVE);
        }
        sourceMapper.deactivate(source);
        sourceRepository.saveAndFlush(source);
    }

    @Transactional
    public SourceAvailabilityResponse test(UUID id) {
        MeasurementSource source = getSourceById(id);
        Instant checkedAt = Instant.now();
        boolean available = source.getType() == MeasurementSourceType.SIMULATOR
                && source.getStatus() != MeasurementSourceStatus.INACTIVE;
        String message = available
                ? Constants.Messages.SIMULATOR_SOURCE_AVAILABLE
                : Constants.Messages.SOURCE_UNAVAILABLE;

        if (available) {
            sourceMapper.recordProbeSuccess(source, checkedAt);
        } else {
            sourceMapper.recordProbeFailure(source, checkedAt, message);
        }
        sourceRepository.saveAndFlush(source);

        return SourceAvailabilityResponse.builder()
                .sourceId(source.getId())
                .available(available)
                .message(message)
                .checkedAt(checkedAt)
                .build();
    }

    @Transactional
    public CollectorJobResponse synchronize(UUID id, CollectorSyncRequest request) {
        MeasurementSource source = getSourceById(id);
        if (source.getStatus() != MeasurementSourceStatus.ACTIVE) {
            throw new BusinessRuleViolationException(Constants.Messages.INACTIVE_SOURCE_SYNCHRONIZATION);
        }

        int samplesPerServer = Objects.isNull(request) || Objects.isNull(request.samplesPerServer())
                ? DEFAULT_SAMPLES_PER_SERVER
                : request.samplesPerServer();
        Instant startedAt = Instant.now();
        CollectorJob job = jobMapper.createStarted(source, startedAt);
        job = jobRepository.saveAndFlush(job);

        try {
            List<EnergyMeasurement> measurements = telemetryCollector.collect(source, samplesPerServer);
            measurementRepository.saveAll(measurements);
            Instant finishedAt = Instant.now();
            jobMapper.markSucceeded(job, finishedAt, measurements.size());
            sourceMapper.recordSyncSuccess(source, finishedAt);
        } catch (RuntimeException exception) {
            Instant finishedAt = Instant.now();
            jobMapper.markFailed(job, finishedAt, exception.getMessage());
            sourceMapper.recordSyncFailure(source, finishedAt, exception.getMessage());
        }

        sourceRepository.save(source);
        CollectorJob savedJob = jobRepository.saveAndFlush(job);
        return jobMapper.toResponse(savedJob);
    }

    private MeasurementSource getSourceById(UUID id) {
        return sourceRepository.getMeasurementSourceById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ResourceNames.SOURCE, id));
    }

    private DataCenter getDataCenterById(UUID id) {
        return dataCenterRepository.getDataCenterById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ResourceNames.DATA_CENTER, id));
    }
}
