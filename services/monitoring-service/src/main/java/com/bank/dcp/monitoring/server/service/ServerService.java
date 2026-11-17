package com.bank.dcp.monitoring.server.service;

import com.bank.dcp.monitoring.common.PageableValidator;
import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
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
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ServerService {
    private final ServerRepository repository;
    private final RackRepository rackRepository;
    private final ServerMapper mapper;

    public Page<ServerResponse> list(Pageable pageable) {
        Pageable safePageable = PageableValidator.validate(
                pageable,
                "hostname",
                "hostname",
                "assetTag",
                "status",
                "installedAt",
                "createdAt"
        );

        return repository.findAll(safePageable).map(mapper::toResponse);
    }

    public ServerResponse getById(UUID id) {
        return mapper.toResponse(getServerById(id));
    }

    @Transactional
    public ServerResponse create(ServerRequest request) {
        Server server = mapper.toEntity(request);
        server.setRack(getRackById(request.rackId()));
        Server savedServer = repository.saveAndFlush(server);

        return mapper.toResponse(savedServer);
    }

    @Transactional
    public ServerResponse patch(UUID id, ServerPatchRequest request) {
        Server server = getServerById(id);

        if (Objects.nonNull(request.rackId())) {
            server.setRack(getRackById(request.rackId()));
        }
        if (Objects.nonNull(request.hostname())) {
            server.setHostname(request.hostname());
        }
        if (Objects.nonNull(request.assetTag())) {
            server.setAssetTag(request.assetTag());
        }
        if (Objects.nonNull(request.vendor())) {
            server.setVendor(request.vendor());
        }
        if (Objects.nonNull(request.model())) {
            server.setModel(request.model());
        }
        if (Objects.nonNull(request.cpuCores())) {
            server.setCpuCores(request.cpuCores());
        }
        if (Objects.nonNull(request.ramGb())) {
            server.setRamGb(request.ramGb());
        }
        if (Objects.nonNull(request.ratedPowerW())) {
            server.setRatedPowerW(request.ratedPowerW());
        }
        if (Objects.nonNull(request.status())) {
            server.setStatus(request.status());
        }
        if (Objects.nonNull(request.installedAt())) {
            server.setInstalledAt(request.installedAt());
        }

        Server savedServer = repository.saveAndFlush(server);
        return mapper.toResponse(savedServer);
    }

    @Transactional
    public void deactivate(UUID id) {
        Server server = getServerById(id);
        server.setStatus(InfrastructureStatus.INACTIVE);
        repository.saveAndFlush(server);
    }

    private Server getServerById(UUID id) {
        return repository.getServerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Server", id));
    }

    private Rack getRackById(UUID id) {
        return rackRepository.getRackById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Rack", id));
    }
}
