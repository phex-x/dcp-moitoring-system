package com.bank.dcp.monitoring.collector.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import com.bank.dcp.monitoring.collector.entity.CollectorJob;
import com.bank.dcp.monitoring.collector.dto.CollectorJobRequest;
import com.bank.dcp.monitoring.collector.dto.CollectorJobResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CollectorJobMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "source", ignore = true)
    CollectorJob toEntity(CollectorJobRequest request);

    @Mapping(target = "sourceId", source = "source.id")
    CollectorJobResponse toResponse(CollectorJob entity);
}
