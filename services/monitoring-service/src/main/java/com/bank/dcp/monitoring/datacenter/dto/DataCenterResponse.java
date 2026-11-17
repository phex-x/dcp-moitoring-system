package com.bank.dcp.monitoring.datacenter.dto;

import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
public record DataCenterResponse(
        UUID id,
        String code,
        String name,
        String location,
        String timezone,
        BigDecimal pueTarget,
        InfrastructureStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
