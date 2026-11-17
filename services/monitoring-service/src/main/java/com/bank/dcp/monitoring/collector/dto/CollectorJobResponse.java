package com.bank.dcp.monitoring.collector.dto;

import java.time.Instant;
import java.util.UUID;

public record CollectorJobResponse(
        UUID id,
        UUID sourceId,
        Instant startedAt,
        Instant finishedAt,
        Long recordsReceived,
        Long recordsSaved,
        Long recordsRejected,
        String status,
        String errorMessage
) {
}
