package com.bank.dcp.monitoring.collector.dto;

import com.bank.dcp.monitoring.common.enums.CollectorJobStatus;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
public record CollectorJobRequest(
        UUID sourceId,
        Instant startedAt,
        Instant finishedAt,
        Long recordsReceived,
        Long recordsSaved,
        Long recordsRejected,
        CollectorJobStatus status,
        String errorMessage
) {
}
