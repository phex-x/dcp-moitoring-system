package com.bank.dcp.monitoring.measurement.mapper;

import com.bank.dcp.monitoring.measurement.dto.EnergyMeasurementRequest;
import com.bank.dcp.monitoring.measurement.dto.EnergyMeasurementResponse;
import com.bank.dcp.monitoring.measurement.dto.MeasurementSeriesPointResponse;
import com.bank.dcp.monitoring.measurement.entity.EnergyMeasurement;
import com.bank.dcp.monitoring.server.entity.Server;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface EnergyMeasurementMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "server", source = "server")
    @Mapping(target = "source", source = "source")
    EnergyMeasurement toEntity(EnergyMeasurementRequest request, Server server, MeasurementSource source);

    @Mapping(target = "serverId", source = "server.id")
    @Mapping(target = "sourceId", source = "source.id")
    EnergyMeasurementResponse toResponse(EnergyMeasurement entity);

    @Mapping(target = "measurementId", source = "id")
    @Mapping(target = "serverId", source = "server.id")
    @Mapping(target = "sourceId", source = "source.id")
    MeasurementSeriesPointResponse toSeriesPoint(EnergyMeasurement entity);
}
