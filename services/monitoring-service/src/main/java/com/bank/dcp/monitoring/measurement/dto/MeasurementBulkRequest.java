package com.bank.dcp.monitoring.measurement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import java.util.List;

@Builder
public record MeasurementBulkRequest(
        @NotEmpty
        @Size(max = 1000)
        List<@Valid EnergyMeasurementRequest> measurements
) {}
