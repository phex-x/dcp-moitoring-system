package com.bank.dcp.monitoring.datacenter.controller;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterPatchRequest;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterRequest;
import com.bank.dcp.monitoring.datacenter.dto.DataCenterResponse;
import com.bank.dcp.monitoring.datacenter.service.DataCenterService;
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
@RequestMapping(Constants.Api.DATA_CENTERS)
@RequiredArgsConstructor
public class DataCenterController {
    private final DataCenterService dataCenterService;

    @GetMapping
    public Page<DataCenterResponse> list(@PageableDefault(size = Constants.Pagination.DEFAULT_PAGE_SIZE) Pageable pageable) {
        return dataCenterService.list(pageable);
    }

    @GetMapping(Constants.Api.RESOURCE_ID)
    public DataCenterResponse getById(@PathVariable UUID id) {
        return dataCenterService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DataCenterResponse create(@Valid @RequestBody DataCenterRequest request) {
        return dataCenterService.create(request);
    }

    @PatchMapping(Constants.Api.RESOURCE_ID)
    public DataCenterResponse patch(@PathVariable UUID id, @Valid @RequestBody DataCenterPatchRequest request) {
        return dataCenterService.patch(id, request);
    }

    @DeleteMapping(Constants.Api.RESOURCE_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable UUID id) {
        dataCenterService.deactivate(id);
    }
}
