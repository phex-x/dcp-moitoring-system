package com.bank.dcp.monitoring.source.dto;

import com.bank.dcp.monitoring.common.enums.MeasurementSourceStatus;
import com.bank.dcp.monitoring.common.enums.MeasurementSourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.util.UUID;

@Builder
public record MeasurementSourceRequest(
        @NotNull
        UUID dataCenterId,
        @NotBlank
        @Size(max = 150)
        String name,
        @NotNull
        MeasurementSourceType type,
        @Size(max = 500)
        String endpoint,
        @NotNull
        MeasurementSourceStatus status,
        @Positive
        Integer pollIntervalSec
) {
}
