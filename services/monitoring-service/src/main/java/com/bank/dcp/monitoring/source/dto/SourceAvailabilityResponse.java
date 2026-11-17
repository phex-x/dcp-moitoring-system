package com.bank.dcp.monitoring.source.dto;

import lombok.Builder;
import java.time.Instant;
import java.util.UUID;

@Builder
public record SourceAvailabilityResponse(
        UUID sourceId,
        boolean available,
        String message,
        Instant checkedAt
) {}
