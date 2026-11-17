package com.bank.dcp.monitoring.measurement.dto;

import com.bank.dcp.monitoring.common.enums.MeasurementQualityStatus;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record MeasurementSeriesPointResponse(
        UUID measurementId,
        UUID serverId,
        UUID sourceId,
        Instant measuredAt,
        BigDecimal powerW,
        BigDecimal energyIntervalKwh,
        MeasurementQualityStatus qualityStatus
) {}
