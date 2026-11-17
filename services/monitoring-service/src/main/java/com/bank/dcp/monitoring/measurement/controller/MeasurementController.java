package com.bank.dcp.monitoring.measurement.controller;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.measurement.dto.EnergyMeasurementRequest;
import com.bank.dcp.monitoring.measurement.dto.EnergyMeasurementResponse;
import com.bank.dcp.monitoring.measurement.dto.MeasurementBulkRequest;
import com.bank.dcp.monitoring.measurement.dto.MeasurementBulkResponse;
import com.bank.dcp.monitoring.measurement.dto.MeasurementFilter;
import com.bank.dcp.monitoring.measurement.dto.MeasurementSeriesPointResponse;
import com.bank.dcp.monitoring.measurement.service.MeasurementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping(Constants.Api.MEASUREMENTS)
@RequiredArgsConstructor
public class MeasurementController {
    private final MeasurementService measurementService;

    @GetMapping
    public Page<EnergyMeasurementResponse> list(@Valid @ModelAttribute MeasurementFilter filter, @PageableDefault(size = Constants.Pagination.MEASUREMENT_PAGE_SIZE) Pageable pageable) {
        return measurementService.list(filter, pageable);
    }

    @GetMapping(Constants.Api.SERIES)
    public Page<MeasurementSeriesPointResponse> series(@Valid @ModelAttribute MeasurementFilter filter, @PageableDefault(size = Constants.Pagination.SERIES_PAGE_SIZE) Pageable pageable) {
        return measurementService.series(filter, pageable);
    }

    @GetMapping(Constants.Api.RESOURCE_ID)
    public EnergyMeasurementResponse getById(@PathVariable UUID id) {
        return measurementService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EnergyMeasurementResponse create(@Valid @RequestBody EnergyMeasurementRequest request) {
        return measurementService.create(request);
    }

    @PostMapping(Constants.Api.BULK)
    @ResponseStatus(HttpStatus.CREATED)
    public MeasurementBulkResponse createBulk(@Valid @RequestBody MeasurementBulkRequest request) {
        return measurementService.createBulk(request);
    }
}
