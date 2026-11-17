package com.bank.dcp.monitoring.datacenter.dto;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record RackPatchRequest(
        UUID dataCenterId,
        @Size(max = Constants.Validation.MAX_CODE_LENGTH)
        String code,
        @Size(max = Constants.Validation.MAX_NAME_LENGTH)
        String name,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        BigDecimal powerCapacityW,
        InfrastructureStatus status
) {}
