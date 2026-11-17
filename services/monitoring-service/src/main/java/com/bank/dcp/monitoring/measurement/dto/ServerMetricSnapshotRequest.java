package com.bank.dcp.monitoring.measurement.dto;

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
        @DecimalMin("0.0")
        @DecimalMax("100.0")
        BigDecimal cpuLoadPct,
        @DecimalMin("0.0")
        @DecimalMax("100.0")
        BigDecimal memoryUsagePct,
        @DecimalMin("-50.0")
        @DecimalMax("150.0")
        BigDecimal temperatureC,
        @DecimalMin("0.0")
        BigDecimal networkInMbps,
        @DecimalMin("0.0")
        BigDecimal networkOutMbps
) {
}
