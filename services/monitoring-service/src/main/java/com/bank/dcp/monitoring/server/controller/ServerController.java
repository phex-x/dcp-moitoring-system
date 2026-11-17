package com.bank.dcp.monitoring.server.controller;

import com.bank.dcp.monitoring.server.dto.ServerPatchRequest;
import com.bank.dcp.monitoring.server.dto.ServerRequest;
import com.bank.dcp.monitoring.server.dto.ServerResponse;
import com.bank.dcp.monitoring.server.service.ServerService;
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
@RequestMapping("/api/v1/servers")
@RequiredArgsConstructor
public class ServerController {
    private final ServerService serverService;

    @GetMapping
    public Page<ServerResponse> list(@PageableDefault(size = 20) Pageable pageable) {
        return serverService.list(pageable);
    }

    @GetMapping("/{id}")
    public ServerResponse getById(@PathVariable UUID id) {
        return serverService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServerResponse create(@Valid @RequestBody ServerRequest request) {
        return serverService.create(request);
    }

    @PatchMapping("/{id}")
    public ServerResponse patch(@PathVariable UUID id, @Valid @RequestBody ServerPatchRequest request) {
        return serverService.patch(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivate(@PathVariable UUID id) {
        serverService.deactivate(id);
    }
}
