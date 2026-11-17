package com.bank.dcp.monitoring.source.dto;

import com.bank.dcp.monitoring.common.enums.MeasurementSourceStatus;
import java.util.UUID;

@lombok.Builder
public record MeasurementSourceRequest(
        UUID dataCenterId,
        String name,
        String type,
        String endpoint,
        MeasurementSourceStatus status,
        Integer pollIntervalSec
) {
}
