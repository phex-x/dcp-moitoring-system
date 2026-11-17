package com.bank.dcp.monitoring.datacenter.repository;

import com.bank.dcp.monitoring.datacenter.entity.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface RackRepository extends JpaRepository<Rack, UUID> {
    Optional<Rack> getRackById(UUID id);

}
