package com.bank.dcp.monitoring.datacenter.dto;

import com.bank.dcp.monitoring.common.Constants;
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
        @Size(max = Constants.Validation.MAX_CODE_LENGTH) String code,
        @NotBlank
        @Size(max = Constants.Validation.MAX_NAME_LENGTH) String name,
        @DecimalMin(Constants.Validation.MIN_NON_NEGATIVE)
        BigDecimal powerCapacityW,
        InfrastructureStatus status
) {
}
