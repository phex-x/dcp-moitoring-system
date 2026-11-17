package com.bank.dcp.monitoring.collector.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record CollectorSyncRequest(
        @Min(1)
        @Max(100)
        Integer samplesPerServer
) {}
