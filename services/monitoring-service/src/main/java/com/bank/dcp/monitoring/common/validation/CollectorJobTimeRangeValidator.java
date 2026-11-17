package com.bank.dcp.monitoring.common.validation;

import com.bank.dcp.monitoring.collector.dto.CollectorJobRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CollectorJobTimeRangeValidator implements ConstraintValidator<ValidCollectorJobTimeRange, CollectorJobRequest> {
    @Override
    public boolean isValid(CollectorJobRequest request, ConstraintValidatorContext context) {
        if (Objects.isNull(request)
                || Objects.isNull(request.startedAt())
                || Objects.isNull(request.finishedAt())) {
            return true;
        }

        return !request.finishedAt().isBefore(request.startedAt());
    }
}
