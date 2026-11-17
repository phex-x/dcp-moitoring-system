package com.bank.dcp.monitoring.collector.dto;

import com.bank.dcp.monitoring.common.Constants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;

@Builder
public record CollectorSyncRequest(
        @Min(Constants.Validation.MIN_COLLECTOR_SAMPLES)
        @Max(Constants.Validation.MAX_COLLECTOR_SAMPLES)
        Integer samplesPerServer
) {}
