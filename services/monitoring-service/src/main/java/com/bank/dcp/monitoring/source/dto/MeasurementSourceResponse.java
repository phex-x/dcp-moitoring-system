package com.bank.dcp.monitoring.source.dto;

import com.bank.dcp.monitoring.common.enums.MeasurementSourceStatus;
import com.bank.dcp.monitoring.common.enums.MeasurementSourceType;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
public record MeasurementSourceResponse(
        UUID id,
        UUID dataCenterId,
        String name,
        MeasurementSourceType type,
        String endpoint,
        MeasurementSourceStatus status,
        Integer pollIntervalSec,
        Instant lastSuccessAt,
        Instant lastErrorAt,
        String lastError
) {
}
