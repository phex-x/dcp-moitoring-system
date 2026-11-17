package com.bank.dcp.monitoring.measurement.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record EnergyMeasurementRequest(
        UUID serverId,
        UUID sourceId,
        Instant measuredAt,
        BigDecimal powerW,
        BigDecimal energyIntervalKwh,
        BigDecimal cpuLoadPct,
        BigDecimal memoryUsagePct,
        BigDecimal temperatureC,
        String qualityStatus,
        String checksum
) {
}
