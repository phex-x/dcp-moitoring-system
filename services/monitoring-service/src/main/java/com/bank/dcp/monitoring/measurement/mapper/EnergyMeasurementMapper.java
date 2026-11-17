package com.bank.dcp.monitoring.measurement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.bank.dcp.monitoring.measurement.entity.EnergyMeasurement;
import com.bank.dcp.monitoring.measurement.dto.EnergyMeasurementRequest;
import com.bank.dcp.monitoring.measurement.dto.EnergyMeasurementResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EnergyMeasurementMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "server", ignore = true)
    @Mapping(target = "source", ignore = true)
    EnergyMeasurement toEntity(EnergyMeasurementRequest request);

    @Mapping(target = "serverId", source = "server.id")
    @Mapping(target = "sourceId", source = "source.id")
    EnergyMeasurementResponse toResponse(EnergyMeasurement entity);
}
