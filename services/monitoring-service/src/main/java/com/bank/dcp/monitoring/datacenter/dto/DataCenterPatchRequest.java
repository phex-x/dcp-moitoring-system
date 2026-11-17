package com.bank.dcp.monitoring.datacenter.dto;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.math.BigDecimal;

@Builder
public record DataCenterPatchRequest(
        @Size(max = Constants.Validation.MAX_CODE_LENGTH)
        String code,
        @Size(max = Constants.Validation.MAX_NAME_LENGTH)
        String name,
        @Size(max = Constants.Validation.MAX_LOCATION_LENGTH)
        String location,
        @Size(max = Constants.Validation.MAX_TIMEZONE_LENGTH)
        String timezone,
        @DecimalMin(Constants.Validation.MIN_PUE)
        @DecimalMax(Constants.Validation.MAX_PUE)
        BigDecimal pueTarget,
        InfrastructureStatus status
) {}
