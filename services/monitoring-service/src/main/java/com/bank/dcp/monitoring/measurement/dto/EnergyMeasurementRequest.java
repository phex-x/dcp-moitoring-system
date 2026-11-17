package com.bank.dcp.monitoring.measurement.dto;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.enums.MeasurementQualityStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record EnergyMeasurementRequest(
        @NotNull
        UUID serverId,
        @NotNull
        UUID sourceId,
        @NotNull
        @PastOrPresent
        Instant measuredAt,
        @NotNull
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        BigDecimal powerW,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        BigDecimal energyIntervalKwh,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        @DecimalMax(Constants.Validation.MAX_PERCENT)
        BigDecimal cpuLoadPct,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        @DecimalMax(Constants.Validation.MAX_PERCENT)
        BigDecimal memoryUsagePct,
        @DecimalMin(Constants.Validation.MIN_TEMPERATURE)
        @DecimalMax(Constants.Validation.MAX_TEMPERATURE)
        BigDecimal temperatureC,
        @NotNull
        MeasurementQualityStatus qualityStatus,
        @Size(max = Constants.Validation.SHA_256_LENGTH)
        @Pattern(regexp = Constants.Validation.SHA_256_PATTERN)
        String checksum
) {}
