package com.bank.dcp.monitoring.datacenter.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record RackResponse(
        UUID id,
        UUID dataCenterId,
        String code,
        String name,
        BigDecimal powerCapacityW,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
}
