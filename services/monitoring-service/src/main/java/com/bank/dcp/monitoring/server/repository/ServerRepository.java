package com.bank.dcp.monitoring.server.repository;

import com.bank.dcp.monitoring.server.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;

public interface ServerRepository extends JpaRepository<Server, UUID> {
    Optional<Server> getServerById(UUID id);

    @Query("select server from Server server where server.rack.dataCenter.id = :dataCenterId and server.status = :status")
    List<Server> getAllByDataCenterIdAndStatus(@Param("dataCenterId") UUID dataCenterId, @Param("status") InfrastructureStatus status);

}
