package com.bank.dcp.monitoring.source.mapper;

import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import com.bank.dcp.monitoring.source.dto.MeasurementSourcePatchRequest;
import com.bank.dcp.monitoring.source.dto.MeasurementSourceRequest;
import com.bank.dcp.monitoring.source.dto.MeasurementSourceResponse;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import org.mapstruct.BeanMapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import java.time.Instant;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
@DecoratedWith(MeasurementSourceMapperDecorator.class)
public interface MeasurementSourceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastSuccessAt", ignore = true)
    @Mapping(target = "lastErrorAt", ignore = true)
    @Mapping(target = "lastError", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "type", source = "request.type")
    @Mapping(target = "endpoint", source = "request.endpoint")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "pollIntervalSec", source = "request.pollIntervalSec")
    @Mapping(target = "dataCenter", source = "dataCenter")
    MeasurementSource toEntity(MeasurementSourceRequest request, DataCenter dataCenter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastSuccessAt", ignore = true)
    @Mapping(target = "lastErrorAt", ignore = true)
    @Mapping(target = "lastError", ignore = true)
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "type", source = "request.type")
    @Mapping(target = "endpoint", source = "request.endpoint")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "pollIntervalSec", source = "request.pollIntervalSec")
    @Mapping(target = "dataCenter", source = "dataCenter")
    void patch(MeasurementSourcePatchRequest request, DataCenter dataCenter, @MappingTarget MeasurementSource entity);

    @Mapping(target = "dataCenterId", source = "dataCenter.id")
    MeasurementSourceResponse toResponse(MeasurementSource entity);

    default void deactivate(@MappingTarget MeasurementSource entity) {
        throw new UnsupportedOperationException("Decorator must implement deactivation");
    }

    default void recordProbeSuccess(@MappingTarget MeasurementSource entity, Instant checkedAt) {
        throw new UnsupportedOperationException("Decorator must implement probe updates");
    }

    default void recordProbeFailure(@MappingTarget MeasurementSource entity, Instant checkedAt, String errorMessage) {
        throw new UnsupportedOperationException("Decorator must implement probe updates");
    }

    default void recordSyncSuccess(@MappingTarget MeasurementSource entity, Instant finishedAt) {
        throw new UnsupportedOperationException("Decorator must implement sync updates");
    }

    default void recordSyncFailure(@MappingTarget MeasurementSource entity, Instant finishedAt, String errorMessage) {
        throw new UnsupportedOperationException("Decorator must implement sync updates");
    }
}
