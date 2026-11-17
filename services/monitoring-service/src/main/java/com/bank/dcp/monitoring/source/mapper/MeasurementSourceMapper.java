package com.bank.dcp.monitoring.source.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import com.bank.dcp.monitoring.source.dto.MeasurementSourceRequest;
import com.bank.dcp.monitoring.source.dto.MeasurementSourceResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface MeasurementSourceMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCenter", ignore = true)
    @Mapping(target = "lastSuccessAt", ignore = true)
    @Mapping(target = "lastErrorAt", ignore = true)
    @Mapping(target = "lastError", ignore = true)
    MeasurementSource toEntity(MeasurementSourceRequest request);

    @Mapping(target = "dataCenterId", source = "dataCenter.id")
    MeasurementSourceResponse toResponse(MeasurementSource entity);
}
