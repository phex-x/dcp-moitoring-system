package com.bank.dcp.monitoring.server.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record ServerPatchRequest(
        UUID rackId,
        @Size(max = 120) String hostname,
        @Size(max = 64) String assetTag,
        @Size(max = 100) String vendor,
        @Size(max = 100) String model,
        @Positive Integer cpuCores,
        @DecimalMin("0.0") BigDecimal ramGb,
        @DecimalMin("0.0") BigDecimal ratedPowerW,
        @Size(max = 20) String status,
        Instant installedAt
) {}
