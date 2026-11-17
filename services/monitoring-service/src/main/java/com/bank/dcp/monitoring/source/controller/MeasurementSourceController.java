package com.bank.dcp.monitoring.source.controller;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.collector.dto.CollectorJobResponse;
import com.bank.dcp.monitoring.collector.dto.CollectorSyncRequest;
import com.bank.dcp.monitoring.source.dto.MeasurementSourcePatchRequest;
import com.bank.dcp.monitoring.source.dto.MeasurementSourceRequest;
import com.bank.dcp.monitoring.source.dto.MeasurementSourceResponse;
import com.bank.dcp.monitoring.source.dto.SourceAvailabilityResponse;
import com.bank.dcp.monitoring.source.service.MeasurementSourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping(Constants.Api.SOURCES)
@RequiredArgsConstructor
public class MeasurementSourceController {
    private final MeasurementSourceService measurementSourceService;

    @GetMapping
    public Page<MeasurementSourceResponse> list(@PageableDefault(size = Constants.Pagination.DEFAULT_PAGE_SIZE) Pageable pageable) {
        return measurementSourceService.list(pageable);
    }

    @GetMapping(Constants.Api.RESOURCE_ID)
    public MeasurementSourceResponse getById(@PathVariable UUID id) {
        return measurementSourceService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MeasurementSourceResponse create(@Valid @RequestBody MeasurementSourceRequest request) {
        return measurementSourceService.create(request);
    }

    @PatchMapping(Constants.Api.RESOURCE_ID)
    public MeasurementSourceResponse patch(@PathVariable UUID id, @Valid @RequestBody MeasurementSourcePatchRequest request) {
        return measurementSourceService.patch(id, request);
    }

    @DeleteMapping(Constants.Api.RESOURCE_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable UUID id) {
        measurementSourceService.deactivate(id);
    }

    @PostMapping(Constants.Api.SOURCE_TEST)
    public SourceAvailabilityResponse test(@PathVariable UUID id) {
        return measurementSourceService.test(id);
    }

    @PostMapping(Constants.Api.SOURCE_SYNC)
    public CollectorJobResponse synchronize(@PathVariable UUID id, @Valid @RequestBody(required = false) CollectorSyncRequest request) {
        return measurementSourceService.synchronize(id, request);
    }
}
