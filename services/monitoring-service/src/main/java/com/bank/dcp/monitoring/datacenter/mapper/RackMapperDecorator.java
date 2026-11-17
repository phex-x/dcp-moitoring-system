package com.bank.dcp.monitoring.datacenter.mapper;

import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import com.bank.dcp.monitoring.datacenter.dto.RackPatchRequest;
import com.bank.dcp.monitoring.datacenter.dto.RackRequest;
import com.bank.dcp.monitoring.datacenter.dto.RackResponse;
import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import com.bank.dcp.monitoring.datacenter.entity.Rack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class RackMapperDecorator implements RackMapper {
    @Autowired
    @Qualifier("delegate")
    private RackMapper delegate;

    @Override
    public Rack toEntity(RackRequest request, DataCenter dataCenter) {
        return delegate.toEntity(request, dataCenter);
    }

    @Override
    public void patch(RackPatchRequest request, DataCenter dataCenter, Rack entity) {
        delegate.patch(request, dataCenter, entity);
    }

    @Override
    public RackResponse toResponse(Rack entity) {
        return delegate.toResponse(entity);
    }

    @Override
    public void deactivate(Rack entity) {
        entity.setStatus(InfrastructureStatus.INACTIVE);
    }
}
