package com.bank.dcp.monitoring.source.mapper;

import com.bank.dcp.monitoring.common.enums.MeasurementSourceStatus;
import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import com.bank.dcp.monitoring.source.dto.MeasurementSourcePatchRequest;
import com.bank.dcp.monitoring.source.dto.MeasurementSourceRequest;
import com.bank.dcp.monitoring.source.dto.MeasurementSourceResponse;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
public abstract class MeasurementSourceMapperDecorator implements MeasurementSourceMapper {
    @Autowired
    @Qualifier("delegate")
    private MeasurementSourceMapper delegate;

    @Override
    public MeasurementSource toEntity(MeasurementSourceRequest request, DataCenter dataCenter) {
        return delegate.toEntity(request, dataCenter);
    }

    @Override
    public void patch(MeasurementSourcePatchRequest request, DataCenter dataCenter, MeasurementSource entity) {
        delegate.patch(request, dataCenter, entity);
    }

    @Override
    public MeasurementSourceResponse toResponse(MeasurementSource entity) {
        return delegate.toResponse(entity);
    }

    @Override
    public void deactivate(MeasurementSource entity) {
        entity.setStatus(MeasurementSourceStatus.INACTIVE);
    }

    @Override
    public void recordProbeSuccess(MeasurementSource entity, Instant checkedAt) {
        entity.setLastSuccessAt(checkedAt);
        entity.setLastError(null);
    }

    @Override
    public void recordProbeFailure(MeasurementSource entity, Instant checkedAt, String errorMessage) {
        entity.setLastErrorAt(checkedAt);
        entity.setLastError(errorMessage);
    }

    @Override
    public void recordSyncSuccess(MeasurementSource entity, Instant finishedAt) {
        entity.setLastSuccessAt(finishedAt);
        entity.setLastError(null);
    }

    @Override
    public void recordSyncFailure(MeasurementSource entity, Instant finishedAt, String errorMessage) {
        entity.setLastErrorAt(finishedAt);
        entity.setLastError(errorMessage);
    }
}
