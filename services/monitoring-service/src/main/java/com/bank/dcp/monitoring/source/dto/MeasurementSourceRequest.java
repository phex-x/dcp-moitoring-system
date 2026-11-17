package com.bank.dcp.monitoring.source.dto;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.enums.MeasurementSourceStatus;
import com.bank.dcp.monitoring.common.enums.MeasurementSourceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.util.UUID;

@Builder
public record MeasurementSourceRequest(
        @NotNull
        UUID dataCenterId,
        @NotBlank
        @Size(max = Constants.Validation.MAX_NAME_LENGTH)
        String name,
        @NotNull
        MeasurementSourceType type,
        @Size(max = Constants.Validation.MAX_DESCRIPTION_LENGTH)
        String endpoint,
        @NotNull
        MeasurementSourceStatus status,
        @Min(Constants.Validation.MIN_POLL_INTERVAL_SECONDS)
        @Max(Constants.Validation.MAX_POLL_INTERVAL_SECONDS)
        Integer pollIntervalSec
) {
}
