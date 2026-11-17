package com.bank.dcp.monitoring.common;

import com.bank.dcp.monitoring.common.exception.BusinessRuleViolationException;
import com.bank.dcp.monitoring.common.exception.InvalidRequestException;
import com.bank.dcp.monitoring.common.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class MonitoringExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<ProblemDetail> notFound(ResourceNotFoundException exception) {
        return problem(HttpStatus.NOT_FOUND, "NOT_FOUND", exception.getMessage());
    }

    @ExceptionHandler({InvalidRequestException.class, MethodArgumentNotValidException.class})
    ResponseEntity<ProblemDetail> invalidRequest(Exception exception) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage());
        detail.setTitle("Validation failed");
        detail.setType(URI.create("about:blank"));
        detail.setProperty("code", "VALIDATION_ERROR");
        if (exception instanceof MethodArgumentNotValidException validationException) {
            Map<String, String> fields = new LinkedHashMap<>();
            validationException.getBindingResult().getFieldErrors().forEach(error -> fields.putIfAbsent(error.getField(), error.getDefaultMessage()));
            detail.setProperty("fields", fields);
        }
        return ResponseEntity.badRequest().body(detail);
    }

    @ExceptionHandler(BusinessRuleViolationException.class)
    ResponseEntity<ProblemDetail> businessRuleViolation(BusinessRuleViolationException exception) {
        return problem(HttpStatus.UNPROCESSABLE_ENTITY, "BUSINESS_RULE_VIOLATION", exception.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<ProblemDetail> conflict(DataIntegrityViolationException exception) {
        return problem(HttpStatus.CONFLICT, "RESOURCE_CONFLICT", "A resource with the same unique value already exists");
    }

    private ResponseEntity<ProblemDetail> problem(HttpStatus status, String code, String message) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(status, message);
        detail.setType(URI.create("about:blank"));
        detail.setProperty("code", code);
        return ResponseEntity.status(status).body(detail);
    }
}
