package com.bank.dcp.monitoring.datacenter.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record RackPatchRequest(
        UUID dataCenterId,
        @Size(max = 32) String code,
        @Size(max = 150) String name,
        @DecimalMin("0.0") BigDecimal powerCapacityW,
        @Size(max = 20) String status
) {}
