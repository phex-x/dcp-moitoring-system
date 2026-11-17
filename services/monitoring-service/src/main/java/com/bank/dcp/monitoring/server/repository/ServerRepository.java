package com.bank.dcp.monitoring.server.repository;

import com.bank.dcp.monitoring.server.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;

public interface ServerRepository extends JpaRepository<Server, UUID> {
    Optional<Server> getServerById(UUID id);

    List<Server> getAllByRack_DataCenter_IdAndStatus(UUID dataCenterId, InfrastructureStatus status);

}
