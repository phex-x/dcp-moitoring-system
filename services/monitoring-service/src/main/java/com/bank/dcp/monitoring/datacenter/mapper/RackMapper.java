package com.bank.dcp.monitoring.datacenter.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.bank.dcp.monitoring.datacenter.entity.Rack;
import com.bank.dcp.monitoring.datacenter.dto.RackRequest;
import com.bank.dcp.monitoring.datacenter.dto.RackResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface RackMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "dataCenter", ignore = true)
    Rack toEntity(RackRequest request);

    @Mapping(target = "dataCenterId", source = "dataCenter.id")
    RackResponse toResponse(Rack entity);
}
