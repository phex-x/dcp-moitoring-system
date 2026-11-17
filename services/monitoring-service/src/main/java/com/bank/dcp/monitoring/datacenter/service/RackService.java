package com.bank.dcp.monitoring.datacenter.service;

import com.bank.dcp.monitoring.common.PageableValidator;
import com.bank.dcp.monitoring.common.exception.ResourceNotFoundException;
import com.bank.dcp.monitoring.datacenter.dto.RackPatchRequest;
import com.bank.dcp.monitoring.datacenter.dto.RackRequest;
import com.bank.dcp.monitoring.datacenter.dto.RackResponse;
import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import com.bank.dcp.monitoring.datacenter.entity.Rack;
import com.bank.dcp.monitoring.datacenter.mapper.RackMapper;
import com.bank.dcp.monitoring.datacenter.repository.DataCenterRepository;
import com.bank.dcp.monitoring.datacenter.repository.RackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RackService {
    private final RackRepository repository;
    private final DataCenterRepository dataCenterRepository;
    private final RackMapper mapper;

    public Page<RackResponse> list(Pageable pageable) {
        Pageable safePageable = PageableValidator.validate(pageable, "code", "code", "name", "status", "createdAt");
        return repository.findAll(safePageable).map(mapper::toResponse);
    }

    public RackResponse get(UUID id) { return mapper.toResponse(find(id)); }

    @Transactional
    public RackResponse create(RackRequest request) {
        Rack rack = mapper.toEntity(request);
        rack.setDataCenter(findDataCenter(request.dataCenterId()));
        return mapper.toResponse(repository.saveAndFlush(rack));
    }

    @Transactional
    public RackResponse patch(UUID id, RackPatchRequest request) {
        Rack rack = find(id);
        if (request.dataCenterId() != null) rack.setDataCenter(findDataCenter(request.dataCenterId()));
        if (request.code() != null) rack.setCode(request.code());
        if (request.name() != null) rack.setName(request.name());
        if (request.powerCapacityW() != null) rack.setPowerCapacityW(request.powerCapacityW());
        if (request.status() != null) rack.setStatus(request.status());
        return mapper.toResponse(repository.saveAndFlush(rack));
    }

    @Transactional
    public void deactivate(UUID id) {
        Rack rack = find(id);
        rack.setStatus("INACTIVE");
        repository.saveAndFlush(rack);
    }

    private Rack find(UUID id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rack", id)); }
    private DataCenter findDataCenter(UUID id) { return dataCenterRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Data center", id)); }
}
