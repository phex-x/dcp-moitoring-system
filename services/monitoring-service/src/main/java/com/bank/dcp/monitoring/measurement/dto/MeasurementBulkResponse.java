package com.bank.dcp.monitoring.measurement.dto;

import lombok.Builder;
import java.util.List;
import java.util.UUID;

@Builder
public record MeasurementBulkResponse(
        int receivedCount,
        int savedCount,
        List<UUID> measurementIds
) {}
