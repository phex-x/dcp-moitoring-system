package com.bank.dcp.monitoring.datacenter.dto;

import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record RackRequest(
        @NotNull
        UUID dataCenterId,
        @NotBlank
        @Size(max = 32) String code,
        @NotBlank
        @Size(max = 150) String name,
        @DecimalMin("0.0") BigDecimal powerCapacityW,
        @NotNull
        InfrastructureStatus status
) {
}
