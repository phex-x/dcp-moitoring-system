package com.bank.dcp.monitoring.datacenter.repository;

import com.bank.dcp.monitoring.datacenter.entity.Rack;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface RackRepository extends JpaRepository<Rack, UUID> {
}
