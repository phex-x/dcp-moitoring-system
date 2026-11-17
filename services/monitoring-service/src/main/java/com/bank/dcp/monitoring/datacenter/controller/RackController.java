package com.bank.dcp.monitoring.datacenter.controller;

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
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/racks")
@RequiredArgsConstructor
public class RackController {
    private final RackService service;
    @GetMapping public Page<RackResponse> list(@PageableDefault(size = 20) Pageable pageable) { return service.list(pageable); }
    @GetMapping("/{id}") public RackResponse get(@PathVariable UUID id) { return service.get(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public RackResponse create(@Valid @RequestBody RackRequest request) { return service.create(request); }
    @PatchMapping("/{id}") public RackResponse patch(@PathVariable UUID id, @Valid @RequestBody RackPatchRequest request) { return service.patch(id, request); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void deactivate(@PathVariable UUID id) { service.deactivate(id); }
}
