package com.bank.dcp.monitoring.server.dto;

import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record ServerRequest(
        @NotNull
        UUID rackId,
        @NotBlank
        @Size(max = 120) String hostname,
        @NotBlank
        @Size(max = 64) String assetTag,
        @Size(max = 100) String vendor,
        @Size(max = 100) String model,
        @Positive Integer cpuCores,
        @DecimalMin("0.0") BigDecimal ramGb,
        @DecimalMin("0.0") BigDecimal ratedPowerW,
        @NotNull
        InfrastructureStatus status,
        Instant installedAt
) {
}
