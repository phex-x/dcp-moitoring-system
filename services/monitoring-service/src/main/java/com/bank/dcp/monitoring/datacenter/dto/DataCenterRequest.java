package com.bank.dcp.monitoring.datacenter.dto;

import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import java.math.BigDecimal;

@Builder
public record DataCenterRequest(
        @NotBlank
        @Size(max = 32) String code,
        @NotBlank
        @Size(max = 150) String name,
        @Size(max = 255) String location,
        @Size(max = 64) String timezone,
        @DecimalMin("1.0")
        @DecimalMax("10.0")
        BigDecimal pueTarget,
        @NotNull
        InfrastructureStatus status
) {
}
