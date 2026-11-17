package com.bank.dcp.monitoring.measurement.dto;

import com.bank.dcp.monitoring.common.enums.MeasurementQualityStatus;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
        Instant measuredAt,
        @NotNull
        @DecimalMin("0.0")
        BigDecimal powerW,
        @DecimalMin("0.0")
        BigDecimal energyIntervalKwh,
        @DecimalMin("0.0")
        @DecimalMax("100.0")
        BigDecimal cpuLoadPct,
        @DecimalMin("0.0")
        @DecimalMax("100.0")
        BigDecimal memoryUsagePct,
        BigDecimal temperatureC,
        @NotNull
        MeasurementQualityStatus qualityStatus,
        @Size(max = 64)
        @Pattern(regexp = "[a-fA-F0-9]{64}")
        String checksum
) {}
