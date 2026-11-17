package com.bank.dcp.monitoring.server.repository;

import com.bank.dcp.monitoring.server.entity.Server;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ServerRepository extends JpaRepository<Server, UUID> {
}
