package com.bank.dcp.monitoring.measurement.dto;

import com.bank.dcp.monitoring.common.enums.MeasurementQualityStatus;
import jakarta.validation.constraints.DecimalMin;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record MeasurementFilter(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Instant from,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        Instant to,
        UUID dataCenterId,
        UUID rackId,
        UUID serverId,
        UUID sourceId,
        @DecimalMin("0.0")
        BigDecimal minPower,
        @DecimalMin("0.0")
        BigDecimal maxPower,
        MeasurementQualityStatus qualityStatus
) {}
