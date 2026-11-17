package com.bank.dcp.monitoring.measurement.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
public record ServerMetricSnapshotResponse(
        UUID id,
        UUID serverId,
        Instant capturedAt,
        BigDecimal cpuLoadPct,
        BigDecimal memoryUsagePct,
        BigDecimal temperatureC,
        BigDecimal networkInMbps,
        BigDecimal networkOutMbps
) {
}
