package com.bank.dcp.monitoring.measurement.dto;

import com.bank.dcp.monitoring.common.Constants;
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
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        BigDecimal minPower,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        BigDecimal maxPower,
        MeasurementQualityStatus qualityStatus
) {}
