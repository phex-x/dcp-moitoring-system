package com.bank.dcp.monitoring.collector.dto;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.enums.CollectorJobStatus;
import com.bank.dcp.monitoring.common.validation.ValidCollectorJobTimeRange;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.time.Instant;
import java.util.UUID;

@lombok.Builder
@ValidCollectorJobTimeRange
public record CollectorJobRequest(
        @NotNull
        UUID sourceId,
        @NotNull
        @PastOrPresent
        Instant startedAt,
        @PastOrPresent
        Instant finishedAt,
        @Min(Constants.Validation.MIN_NON_NEGATIVE_COUNT)
        Long recordsReceived,
        @Min(Constants.Validation.MIN_NON_NEGATIVE_COUNT)
        Long recordsSaved,
        @Min(Constants.Validation.MIN_NON_NEGATIVE_COUNT)
        Long recordsRejected,
        @NotNull
        CollectorJobStatus status,
        String errorMessage
) {
}
