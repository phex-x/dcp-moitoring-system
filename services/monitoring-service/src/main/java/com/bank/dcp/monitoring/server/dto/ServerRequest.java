package com.bank.dcp.monitoring.server.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
public record ServerRequest(
        UUID rackId,
        String hostname,
        String assetTag,
        String vendor,
        String model,
        Integer cpuCores,
        BigDecimal ramGb,
        BigDecimal ratedPowerW,
        String status,
        Instant installedAt
) {
}
