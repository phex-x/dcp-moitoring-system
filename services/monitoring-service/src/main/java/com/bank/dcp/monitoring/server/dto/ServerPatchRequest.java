package com.bank.dcp.monitoring.server.dto;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Builder
public record ServerPatchRequest(
        UUID rackId,
        @Size(max = Constants.Validation.MAX_HOSTNAME_LENGTH)
        String hostname,
        @Size(max = Constants.Validation.MAX_ASSET_TAG_LENGTH)
        String assetTag,
        @Size(max = Constants.Validation.MAX_VENDOR_LENGTH)
        String vendor,
        @Size(max = Constants.Validation.MAX_VENDOR_LENGTH)
        String model,
        @Positive
        Integer cpuCores,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        BigDecimal ramGb,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        BigDecimal ratedPowerW,
        InfrastructureStatus status,
        @PastOrPresent
        Instant installedAt
) {}
