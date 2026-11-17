package com.bank.dcp.monitoring.datacenter.mapper;

import com.bank.dcp.monitoring.datacenter.dto.DataCenterPatchRequest;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterRequest;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterResponse;
import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import org.mapstruct.BeanMapping;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
@DecoratedWith(DataCenterMapperDecorator.class)
public interface DataCenterMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DataCenter toEntity(DataCenterRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void patch(DataCenterPatchRequest request, @MappingTarget DataCenter entity);

    DataCenterResponse toResponse(DataCenter entity);

    default void deactivate(@MappingTarget DataCenter entity) {
        throw new UnsupportedOperationException("Decorator must implement deactivation");
    }
}
