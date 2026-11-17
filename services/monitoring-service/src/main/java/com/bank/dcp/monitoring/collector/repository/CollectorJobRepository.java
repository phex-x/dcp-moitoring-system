package com.bank.dcp.monitoring.collector.repository;

import com.bank.dcp.monitoring.collector.entity.CollectorJob;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CollectorJobRepository extends JpaRepository<CollectorJob, UUID> {
}
