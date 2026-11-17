package com.bank.dcp.monitoring.measurement.dto;

import com.bank.dcp.monitoring.common.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.util.List;

@Builder
public record MeasurementBulkRequest(
        @NotEmpty
        @Size(max = Constants.Pagination.MAX_BULK_SIZE)
        List<@Valid EnergyMeasurementRequest> measurements
) {}
