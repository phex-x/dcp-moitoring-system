package com.bank.dcp.monitoring.source.dto;

import java.time.Instant;
import java.util.UUID;

public record MeasurementSourceResponse(
        UUID id,
        UUID dataCenterId,
        String name,
        String type,
        String endpoint,
        String status,
        Integer pollIntervalSec,
        Instant lastSuccessAt,
        Instant lastErrorAt,
        String lastError
) {
}
