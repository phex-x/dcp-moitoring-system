package com.bank.dcp.monitoring.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = CollectorJobTimeRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCollectorJobTimeRange {
    String message() default "finishedAt must be greater than or equal to startedAt";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
