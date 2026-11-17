package com.bank.dcp.monitoring.datacenter.dto;

import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import java.math.BigDecimal;

@lombok.Builder
public record DataCenterRequest(
        @jakarta.validation.constraints.NotBlank
        @jakarta.validation.constraints.Size(max = 32) String code,
        @jakarta.validation.constraints.NotBlank
        @jakarta.validation.constraints.Size(max = 150) String name,
        @jakarta.validation.constraints.Size(max = 255) String location,
        @jakarta.validation.constraints.Size(max = 64) String timezone,
        @jakarta.validation.constraints.DecimalMin("0.0") BigDecimal pueTarget,
        @jakarta.validation.constraints.NotNull
        InfrastructureStatus status
) {
}
