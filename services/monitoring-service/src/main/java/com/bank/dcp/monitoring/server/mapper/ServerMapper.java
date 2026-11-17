package com.bank.dcp.monitoring.server.mapper;

import com.bank.dcp.monitoring.datacenter.entity.Rack;
import com.bank.dcp.monitoring.server.dto.ServerPatchRequest;
import com.bank.dcp.monitoring.server.dto.ServerRequest;
import com.bank.dcp.monitoring.server.dto.ServerResponse;
import com.bank.dcp.monitoring.server.entity.Server;
import org.mapstruct.BeanMapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
@DecoratedWith(ServerMapperDecorator.class)
public interface ServerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "hostname", source = "request.hostname")
    @Mapping(target = "assetTag", source = "request.assetTag")
    @Mapping(target = "vendor", source = "request.vendor")
    @Mapping(target = "model", source = "request.model")
    @Mapping(target = "cpuCores", source = "request.cpuCores")
    @Mapping(target = "ramGb", source = "request.ramGb")
    @Mapping(target = "ratedPowerW", source = "request.ratedPowerW")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "installedAt", source = "request.installedAt")
    @Mapping(target = "rack", source = "rack")
    Server toEntity(ServerRequest request, Rack rack);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "hostname", source = "request.hostname")
    @Mapping(target = "assetTag", source = "request.assetTag")
    @Mapping(target = "vendor", source = "request.vendor")
    @Mapping(target = "model", source = "request.model")
    @Mapping(target = "cpuCores", source = "request.cpuCores")
    @Mapping(target = "ramGb", source = "request.ramGb")
    @Mapping(target = "ratedPowerW", source = "request.ratedPowerW")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "installedAt", source = "request.installedAt")
    @Mapping(target = "rack", source = "rack")
    void patch(ServerPatchRequest request, Rack rack, @MappingTarget Server entity);

    @Mapping(target = "rackId", source = "rack.id")
    ServerResponse toResponse(Server entity);

    default void deactivate(@MappingTarget Server entity) {
        throw new UnsupportedOperationException("Decorator must implement deactivation");
    }
}
