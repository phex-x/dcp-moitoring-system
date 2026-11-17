package com.bank.dcp.monitoring.datacenter.service;

import com.bank.dcp.monitoring.common.PageableValidator;
import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import com.bank.dcp.monitoring.common.exception.ResourceNotFoundException;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterPatchRequest;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterRequest;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterResponse;
import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import com.bank.dcp.monitoring.datacenter.mapper.DataCenterMapper;
import com.bank.dcp.monitoring.datacenter.repository.DataCenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataCenterService {
    private final DataCenterRepository repository;
    private final DataCenterMapper mapper;

    public Page<DataCenterResponse> list(Pageable pageable) {
        Pageable safePageable = PageableValidator.validate(
                pageable,
                "name",
                "code",
                "name",
                "location",
                "status",
                "createdAt"
        );

        return repository.findAll(safePageable).map(mapper::toResponse);
    }

    public DataCenterResponse getById(UUID id) {
        return mapper.toResponse(getDataCenterById(id));
    }

    @Transactional
    public DataCenterResponse create(DataCenterRequest request) {
        DataCenter entity = mapper.toEntity(request);
        DataCenter savedEntity = repository.saveAndFlush(entity);

        return mapper.toResponse(savedEntity);
    }

    @Transactional
    public DataCenterResponse patch(UUID id, DataCenterPatchRequest request) {
        DataCenter entity = getDataCenterById(id);

        if (Objects.nonNull(request.code())) {
            entity.setCode(request.code());
        }
        if (Objects.nonNull(request.name())) {
            entity.setName(request.name());
        }
        if (Objects.nonNull(request.location())) {
            entity.setLocation(request.location());
        }
        if (Objects.nonNull(request.timezone())) {
            entity.setTimezone(request.timezone());
        }
        if (Objects.nonNull(request.pueTarget())) {
            entity.setPueTarget(request.pueTarget());
        }
        if (Objects.nonNull(request.status())) {
            entity.setStatus(request.status());
        }

        DataCenter savedEntity = repository.saveAndFlush(entity);
        return mapper.toResponse(savedEntity);
    }

    @Transactional
    public void deactivate(UUID id) {
        DataCenter entity = getDataCenterById(id);
        entity.setStatus(InfrastructureStatus.INACTIVE);
        repository.saveAndFlush(entity);
    }

    private DataCenter getDataCenterById(UUID id) {
        return repository.getDataCenterById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Data center", id));
    }
}
