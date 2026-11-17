package com.bank.dcp.monitoring.collector.mapper;

import com.bank.dcp.monitoring.collector.dto.CollectorJobResponse;
import com.bank.dcp.monitoring.collector.entity.CollectorJob;
import com.bank.dcp.monitoring.common.enums.CollectorJobStatus;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.time.Instant;

@Component
public abstract class CollectorJobMapperDecorator implements CollectorJobMapper {
    @Autowired
    @Qualifier("delegate")
    private CollectorJobMapper delegate;

    @Override
    public CollectorJob createStarted(MeasurementSource source, Instant startedAt) {
        return CollectorJob.builder()
                .source(source)
                .startedAt(startedAt)
                .status(CollectorJobStatus.RUNNING)
                .recordsReceived(0L)
                .recordsSaved(0L)
                .recordsRejected(0L)
                .build();
    }

    @Override
    public CollectorJobResponse toResponse(CollectorJob entity) {
        return delegate.toResponse(entity);
    }

    @Override
    public void markSucceeded(CollectorJob job, Instant finishedAt, long recordCount) {
        job.setFinishedAt(finishedAt);
        job.setRecordsReceived(recordCount);
        job.setRecordsSaved(recordCount);
        job.setRecordsRejected(0L);
        job.setStatus(CollectorJobStatus.SUCCEEDED);
    }

    @Override
    public void markFailed(CollectorJob job, Instant finishedAt, String errorMessage) {
        job.setFinishedAt(finishedAt);
        job.setStatus(CollectorJobStatus.FAILED);
        job.setErrorMessage(errorMessage);
    }
}
