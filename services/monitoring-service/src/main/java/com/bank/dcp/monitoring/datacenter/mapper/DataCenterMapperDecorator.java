package com.bank.dcp.monitoring.datacenter.mapper;

import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterPatchRequest;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterRequest;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterResponse;
import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.Objects;

@Component
public abstract class DataCenterMapperDecorator implements DataCenterMapper {
    @Autowired
    @Qualifier("delegate")
    private DataCenterMapper delegate;

    @Override
    public DataCenter toEntity(DataCenterRequest request) {
        return delegate.toEntity(request);
    }

    @Override
    public void patch(DataCenterPatchRequest request, DataCenter entity) {
        delegate.patch(request, entity);
    }

    @Override
    public DataCenterResponse toResponse(DataCenter entity) {
        return delegate.toResponse(entity);
    }

    @Override
    public void deactivate(DataCenter entity) {
        entity.setStatus(InfrastructureStatus.INACTIVE);
    }
}
