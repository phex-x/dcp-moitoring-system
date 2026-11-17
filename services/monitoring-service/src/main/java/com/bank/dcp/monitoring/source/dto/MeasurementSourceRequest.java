package com.bank.dcp.monitoring.source.dto;

import java.util.UUID;

@lombok.Builder
public record MeasurementSourceRequest(
        UUID dataCenterId,
        String name,
        String type,
        String endpoint,
        String status,
        Integer pollIntervalSec
) {
}
