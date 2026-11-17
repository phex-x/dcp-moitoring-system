package com.bank.dcp.monitoring.collector.mapper;

import com.bank.dcp.monitoring.collector.dto.CollectorJobResponse;
import com.bank.dcp.monitoring.collector.entity.CollectorJob;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import java.time.Instant;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
@DecoratedWith(CollectorJobMapperDecorator.class)
public interface CollectorJobMapper {
    default CollectorJob createStarted(MeasurementSource source, Instant startedAt) {
        throw new UnsupportedOperationException("Decorator must create the collector job");
    }

    @Mapping(target = "sourceId", source = "source.id")
    CollectorJobResponse toResponse(CollectorJob entity);

    default void markSucceeded(CollectorJob job, Instant finishedAt, long recordCount) {
        throw new UnsupportedOperationException("Decorator must update the collector job");
    }

    default void markFailed(CollectorJob job, Instant finishedAt, String errorMessage) {
        throw new UnsupportedOperationException("Decorator must update the collector job");
    }
}
