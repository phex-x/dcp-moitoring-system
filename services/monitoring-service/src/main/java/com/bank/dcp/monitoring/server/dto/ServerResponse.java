package com.bank.dcp.monitoring.server.dto;

import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
public record ServerResponse(
        UUID id,
        UUID rackId,
        String hostname,
        String assetTag,
        String vendor,
        String model,
        Integer cpuCores,
        BigDecimal ramGb,
        BigDecimal ratedPowerW,
        InfrastructureStatus status,
        Instant installedAt,
        Instant createdAt,
        Instant updatedAt
) {
}
