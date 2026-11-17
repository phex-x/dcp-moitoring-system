package com.bank.dcp.monitoring.datacenter.service;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.PageableValidator;
import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import com.bank.dcp.monitoring.common.exception.BusinessRuleViolationException;
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
import java.util.Objects;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RackService {
    private final RackRepository repository;
    private final DataCenterRepository dataCenterRepository;
    private final RackMapper mapper;

    public Page<RackResponse> list(Pageable pageable) {
        Pageable safePageable = PageableValidator.validate(
                pageable,
                Constants.SortFields.CODE,
                Constants.SortFields.CODE,
                Constants.SortFields.NAME,
                Constants.SortFields.STATUS,
                Constants.SortFields.CREATED_AT
        );

        return repository.findAll(safePageable).map(mapper::toResponse);
    }

    public RackResponse getById(UUID id) {
        return mapper.toResponse(getRackById(id));
    }

    @Transactional
    public RackResponse create(RackRequest request) {
        DataCenter dataCenter = getDataCenterById(request.dataCenterId());
        Rack rack = mapper.toEntity(request, dataCenter);
        Rack savedRack = repository.saveAndFlush(rack);

        return mapper.toResponse(savedRack);
    }

    @Transactional
    public RackResponse patch(UUID id, RackPatchRequest request) {
        Rack rack = getRackById(id);
        if (Objects.nonNull(request.status()) && !rack.getStatus().canTransitionTo(request.status())) {
            throw new BusinessRuleViolationException(Constants.Messages.INVALID_INFRASTRUCTURE_STATUS_TRANSITION);
        }
        DataCenter dataCenter = Objects.isNull(request.dataCenterId())
                ? null
                : getDataCenterById(request.dataCenterId());
        mapper.patch(request, dataCenter, rack);

        Rack savedRack = repository.saveAndFlush(rack);
        return mapper.toResponse(savedRack);
    }

    @Transactional
    public void deactivate(UUID id) {
        Rack rack = getRackById(id);
        if (!rack.getStatus().canTransitionTo(InfrastructureStatus.INACTIVE)) {
            throw new BusinessRuleViolationException(Constants.Messages.DECOMMISSIONED_INFRASTRUCTURE_DEACTIVATION);
        }
        mapper.deactivate(rack);
        repository.saveAndFlush(rack);
    }

    private Rack getRackById(UUID id) {
        return repository.getRackById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ResourceNames.RACK, id));
    }

    private DataCenter getDataCenterById(UUID id) {
        return dataCenterRepository.getDataCenterById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Constants.ResourceNames.DATA_CENTER, id));
    }
}
