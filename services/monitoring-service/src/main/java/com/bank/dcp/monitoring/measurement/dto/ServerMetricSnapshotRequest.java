package com.bank.dcp.monitoring.measurement.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ServerMetricSnapshotRequest(
        UUID serverId,
        Instant capturedAt,
        BigDecimal cpuLoadPct,
        BigDecimal memoryUsagePct,
        BigDecimal temperatureC,
        BigDecimal networkInMbps,
        BigDecimal networkOutMbps
) {
}
