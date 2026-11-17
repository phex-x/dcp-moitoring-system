package com.bank.dcp.monitoring.measurement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.bank.dcp.monitoring.measurement.entity.ServerMetricSnapshot;
import com.bank.dcp.monitoring.measurement.dto.ServerMetricSnapshotRequest;
import com.bank.dcp.monitoring.measurement.dto.ServerMetricSnapshotResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ServerMetricSnapshotMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "server", ignore = true)
    ServerMetricSnapshot toEntity(ServerMetricSnapshotRequest request);

    @Mapping(target = "serverId", source = "server.id")
    ServerMetricSnapshotResponse toResponse(ServerMetricSnapshot entity);
}
