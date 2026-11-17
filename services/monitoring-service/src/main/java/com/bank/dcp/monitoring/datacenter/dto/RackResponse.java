package com.bank.dcp.monitoring.datacenter.dto;

import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
public record RackResponse(
        UUID id,
        UUID dataCenterId,
        String code,
        String name,
        BigDecimal powerCapacityW,
        InfrastructureStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
