package com.bank.dcp.monitoring.common;

import com.bank.dcp.monitoring.common.exception.BusinessRuleViolationException;
import com.bank.dcp.monitoring.common.exception.InvalidRequestException;
import com.bank.dcp.monitoring.common.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import lombok.RequiredArgsConstructor;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class MonitoringExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ProblemDetail> notFound(ResourceNotFoundException exception) {
        return problem(HttpStatus.NOT_FOUND, Constants.Messages.NOT_FOUND, exception.getMessage());
    }

    @ExceptionHandler({InvalidRequestException.class, MethodArgumentNotValidException.class})
    ResponseEntity<ProblemDetail> invalidRequest(Exception exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        detail.setTitle(message(Constants.Messages.VALIDATION_FAILED));
        detail.setType(URI.create("about:blank"));
        detail.setProperty("code", Constants.Messages.VALIDATION_ERROR);
        if (exception instanceof MethodArgumentNotValidException validationException) {
            Map<String, String> fields = new LinkedHashMap<>();
            validationException.getBindingResult().getFieldErrors().forEach(error -> fields.putIfAbsent(error.getField(), error.getDefaultMessage()));
            detail.setProperty("fields", fields);
        }
        return ResponseEntity.badRequest().body(detail);
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    ResponseEntity<ProblemDetail> businessRuleViolation(BusinessRuleViolationException exception) {
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, Constants.Messages.BUSINESS_RULE_VIOLATION, exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ProblemDetail> conflict(DataIntegrityViolationException exception) {
        return problem(
                HttpStatus.CONFLICT,
                Constants.Messages.RESOURCE_CONFLICT,
                message(Constants.Messages.DUPLICATE_RESOURCE)
        );
    }

    private ResponseEntity<ProblemDetail> problem(HttpStatus status, String code, String message) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, message);
        detail.setType(URI.create("about:blank"));
        detail.setProperty("code", code);
        return ResponseEntity.status(status).body(detail);
    }

    private String message(String code) {
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
}
