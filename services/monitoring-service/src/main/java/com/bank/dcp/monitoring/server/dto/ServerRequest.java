package com.bank.dcp.monitoring.server.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
public record ServerRequest(
        @jakarta.validation.constraints.NotNull UUID rackId,
        @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max = 120) String hostname,
        @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max = 64) String assetTag,
        @jakarta.validation.constraints.Size(max = 100) String vendor,
        @jakarta.validation.constraints.Size(max = 100) String model,
        @jakarta.validation.constraints.Positive Integer cpuCores,
        @jakarta.validation.constraints.DecimalMin("0.0") BigDecimal ramGb,
        @jakarta.validation.constraints.DecimalMin("0.0") BigDecimal ratedPowerW,
        @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max = 20) String status,
        Instant installedAt
) {
}
