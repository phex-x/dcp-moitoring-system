package com.bank.dcp.monitoring.datacenter.controller;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.datacenter.dto.RackPatchRequest;
import com.bank.dcp.monitoring.datacenter.dto.RackRequest;
import com.bank.dcp.monitoring.datacenter.dto.RackResponse;
import com.bank.dcp.monitoring.datacenter.service.RackService;
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
@RequestMapping(Constants.Api.RACKS)
@RequiredArgsConstructor
public class RackController {
    private final RackService rackService;

    @GetMapping
    public Page<RackResponse> list(@PageableDefault(size = Constants.Pagination.DEFAULT_PAGE_SIZE) Pageable pageable) {
        return rackService.list(pageable);
    }

    @GetMapping(Constants.Api.RESOURCE_ID)
    public RackResponse getById(@PathVariable UUID id) {
        return rackService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RackResponse create(@Valid @RequestBody RackRequest request) {
        return rackService.create(request);
    }

    @PatchMapping(Constants.Api.RESOURCE_ID)
    public RackResponse patch(@PathVariable UUID id, @Valid @RequestBody RackPatchRequest request) {
        return rackService.patch(id, request);
    }

    @DeleteMapping(Constants.Api.RESOURCE_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable UUID id) {
        rackService.deactivate(id);
    }
}
