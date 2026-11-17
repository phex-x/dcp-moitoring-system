package com.bank.dcp.monitoring.datacenter.mapper;

import com.bank.dcp.monitoring.datacenter.dto.RackPatchRequest;
import com.bank.dcp.monitoring.datacenter.dto.RackRequest;
import com.bank.dcp.monitoring.datacenter.dto.RackResponse;
import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import com.bank.dcp.monitoring.datacenter.entity.Rack;
import org.mapstruct.BeanMapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
@DecoratedWith(RackMapperDecorator.class)
public interface RackMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "code", source = "request.code")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "powerCapacityW", source = "request.powerCapacityW")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "dataCenter", source = "dataCenter")
    Rack toEntity(RackRequest request, DataCenter dataCenter);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "code", source = "request.code")
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "powerCapacityW", source = "request.powerCapacityW")
    @Mapping(target = "status", source = "request.status")
    @Mapping(target = "dataCenter", source = "dataCenter")
    void patch(RackPatchRequest request, DataCenter dataCenter, @MappingTarget Rack entity);

    @Mapping(target = "dataCenterId", source = "dataCenter.id")
    RackResponse toResponse(Rack entity);

    default void deactivate(@MappingTarget Rack entity) {
        throw new UnsupportedOperationException("Decorator must implement deactivation");
    }
}
