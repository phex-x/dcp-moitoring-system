package com.bank.dcp.monitoring.source.dto;

import com.bank.dcp.monitoring.common.enums.MeasurementSourceStatus;
import com.bank.dcp.monitoring.common.enums.MeasurementSourceType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.util.UUID;

@Builder
public record MeasurementSourcePatchRequest(
        UUID dataCenterId,
        @Size(max = 150)
        String name,
        MeasurementSourceType type,
        @Size(max = 500)
        String endpoint,
        MeasurementSourceStatus status,
        @Min(1)
        @Max(86400)
        Integer pollIntervalSec
) {}
