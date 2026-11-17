package com.bank.dcp.monitoring.measurement.dto;

import com.bank.dcp.monitoring.common.Constants;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
public record ServerMetricSnapshotRequest(
        @NotNull
        UUID serverId,
        @NotNull
        @PastOrPresent
        Instant capturedAt,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        @DecimalMax(Constants.Validation.MAX_PERCENT)
        BigDecimal cpuLoadPct,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        @DecimalMax(Constants.Validation.MAX_PERCENT)
        BigDecimal memoryUsagePct,
        @DecimalMin(Constants.Validation.MIN_TEMPERATURE)
        @DecimalMax(Constants.Validation.MAX_TEMPERATURE)
        BigDecimal temperatureC,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        BigDecimal networkInMbps,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        BigDecimal networkOutMbps
) {
}
