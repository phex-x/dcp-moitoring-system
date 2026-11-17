package com.bank.dcp.monitoring.datacenter.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record DataCenterPatchRequest(
        @Size(max = 32) String code,
        @Size(max = 150) String name,
        @Size(max = 255) String location,
        @Size(max = 64) String timezone,
        @DecimalMin("0.0") BigDecimal pueTarget,
        @Size(max = 20) String status
) {}
