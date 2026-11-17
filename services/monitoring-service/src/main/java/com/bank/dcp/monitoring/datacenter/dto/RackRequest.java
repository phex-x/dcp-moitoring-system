package com.bank.dcp.monitoring.datacenter.dto;

import java.math.BigDecimal;
import java.util.UUID;

@lombok.Builder
public record RackRequest(
        @jakarta.validation.constraints.NotNull UUID dataCenterId,
        @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max = 32) String code,
        @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max = 150) String name,
        @jakarta.validation.constraints.DecimalMin("0.0") BigDecimal powerCapacityW,
        @jakarta.validation.constraints.NotBlank @jakarta.validation.constraints.Size(max = 20) String status
) {
}
