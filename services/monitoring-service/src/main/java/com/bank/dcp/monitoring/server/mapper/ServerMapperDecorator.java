package com.bank.dcp.monitoring.server.mapper;

import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import com.bank.dcp.monitoring.datacenter.entity.Rack;
import com.bank.dcp.monitoring.server.dto.ServerPatchRequest;
import com.bank.dcp.monitoring.server.dto.ServerRequest;
import com.bank.dcp.monitoring.server.dto.ServerResponse;
import com.bank.dcp.monitoring.server.entity.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public abstract class ServerMapperDecorator implements ServerMapper {
    @Autowired
    @Qualifier("delegate")
    private ServerMapper delegate;

    @Override
    public Server toEntity(ServerRequest request, Rack rack) {
        return delegate.toEntity(request, rack);
    }

    @Override
    public void patch(ServerPatchRequest request, Rack rack, Server entity) {
        delegate.patch(request, rack, entity);
    }

    @Override
    public ServerResponse toResponse(Server entity) {
        return delegate.toResponse(entity);
    }

    @Override
    public void deactivate(Server entity) {
        entity.setStatus(InfrastructureStatus.INACTIVE);
    }
}
