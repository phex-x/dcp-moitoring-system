package com.bank.dcp.monitoring.datacenter.controller;

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
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/data-centers")
@RequiredArgsConstructor
public class DataCenterController {
    private final DataCenterService service;
    @GetMapping public Page<DataCenterResponse> list(@PageableDefault(size = 20) Pageable pageable) { return service.list(pageable); }
    @GetMapping("/{id}") public DataCenterResponse get(@PathVariable UUID id) { return service.get(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public DataCenterResponse create(@Valid @RequestBody DataCenterRequest request) { return service.create(request); }
    @PatchMapping("/{id}") public DataCenterResponse patch(@PathVariable UUID id, @Valid @RequestBody DataCenterPatchRequest request) { return service.patch(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deactivate(@PathVariable UUID id) { service.deactivate(id); }
}
