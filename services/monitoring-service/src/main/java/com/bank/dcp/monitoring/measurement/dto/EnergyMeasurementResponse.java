package com.bank.dcp.monitoring.measurement.dto;

import com.bank.dcp.monitoring.common.enums.MeasurementQualityStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
public record EnergyMeasurementResponse(
        UUID id,
        UUID serverId,
        UUID sourceId,
        Instant measuredAt,
        BigDecimal powerW,
        BigDecimal energyIntervalKwh,
        BigDecimal cpuLoadPct,
        BigDecimal memoryUsagePct,
        BigDecimal temperatureC,
        MeasurementQualityStatus qualityStatus,
        String checksum,
        Instant createdAt
) {
}
