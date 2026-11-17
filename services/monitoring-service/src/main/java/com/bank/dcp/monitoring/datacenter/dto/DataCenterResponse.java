package com.bank.dcp.monitoring.datacenter.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record DataCenterResponse(
        UUID id,
        String code,
        String name,
        String location,
        String timezone,
        BigDecimal pueTarget,
        String status,
        Instant createdAt,
        Instant updatedAt
) {
}
