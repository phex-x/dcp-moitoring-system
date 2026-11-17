package com.bank.dcp.monitoring.server.service;

import com.bank.dcp.monitoring.common.PageableValidator;
import com.bank.dcp.monitoring.common.exception.ResourceNotFoundException;
import com.bank.dcp.monitoring.datacenter.entity.Rack;
import com.bank.dcp.monitoring.datacenter.repository.RackRepository;
import com.bank.dcp.monitoring.server.dto.ServerPatchRequest;
import com.bank.dcp.monitoring.server.dto.ServerRequest;
import com.bank.dcp.monitoring.server.dto.ServerResponse;
import com.bank.dcp.monitoring.server.entity.Server;
import com.bank.dcp.monitoring.server.mapper.ServerMapper;
import com.bank.dcp.monitoring.server.repository.ServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServerService {
    private final ServerRepository repository;
    private final RackRepository rackRepository;
    private final ServerMapper mapper;

    public Page<ServerResponse> list(Pageable pageable) {
        Pageable safePageable = PageableValidator.validate(pageable, "hostname", "hostname", "assetTag", "status", "installedAt", "createdAt");
        return repository.findAll(safePageable).map(mapper::toResponse);
    }

    public ServerResponse get(UUID id) { return mapper.toResponse(find(id)); }

    @Transactional
    public ServerResponse create(ServerRequest request) {
        Server server = mapper.toEntity(request);
        server.setRack(findRack(request.rackId()));
        return mapper.toResponse(repository.saveAndFlush(server));
    }

    @Transactional
    public ServerResponse patch(UUID id, ServerPatchRequest request) {
        Server server = find(id);
        if (request.rackId() != null) server.setRack(findRack(request.rackId()));
        if (request.hostname() != null) server.setHostname(request.hostname());
        if (request.assetTag() != null) server.setAssetTag(request.assetTag());
        if (request.vendor() != null) server.setVendor(request.vendor());
        if (request.model() != null) server.setModel(request.model());
        if (request.cpuCores() != null) server.setCpuCores(request.cpuCores());
        if (request.ramGb() != null) server.setRamGb(request.ramGb());
        if (request.ratedPowerW() != null) server.setRatedPowerW(request.ratedPowerW());
        if (request.status() != null) server.setStatus(request.status());
        if (request.installedAt() != null) server.setInstalledAt(request.installedAt());
        return mapper.toResponse(repository.saveAndFlush(server));
    }

    @Transactional
    public void deactivate(UUID id) {
        Server server = find(id);
        server.setStatus("INACTIVE");
        repository.saveAndFlush(server);
    }

    private Server find(UUID id) { return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Server", id)); }
    private Rack findRack(UUID id) { return rackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Rack", id)); }
}
