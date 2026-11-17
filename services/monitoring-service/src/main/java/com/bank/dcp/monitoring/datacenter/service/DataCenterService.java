package com.bank.dcp.monitoring.datacenter.service;

import com.bank.dcp.monitoring.common.PageableValidator;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DataCenterService {
    private final DataCenterRepository repository;
    private final DataCenterMapper mapper;

    public Page<DataCenterResponse> list(Pageable pageable) {
        Pageable safePageable = PageableValidator.validate(pageable, "name", "code", "name", "location", "status", "createdAt");
        return repository.findAll(safePageable).map(mapper::toResponse);
    }

    public DataCenterResponse get(UUID id) { return mapper.toResponse(find(id)); }

    @Transactional
    public DataCenterResponse create(DataCenterRequest request) {
        return mapper.toResponse(repository.saveAndFlush(mapper.toEntity(request)));
    }

    @Transactional
    public DataCenterResponse patch(UUID id, DataCenterPatchRequest request) {
        DataCenter entity = find(id);
        if (request.code() != null) entity.setCode(request.code());
        if (request.name() != null) entity.setName(request.name());
        if (request.location() != null) entity.setLocation(request.location());
        if (request.timezone() != null) entity.setTimezone(request.timezone());
        if (request.pueTarget() != null) entity.setPueTarget(request.pueTarget());
        if (request.status() != null) entity.setStatus(request.status());
        return mapper.toResponse(repository.saveAndFlush(entity));
    }

    @Transactional
    public void deactivate(UUID id) {
        DataCenter entity = find(id);
        entity.setStatus("INACTIVE");
        repository.saveAndFlush(entity);
    }

    private DataCenter find(UUID id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data center", id)); }
}
