package com.bank.dcp.monitoring.measurement.repository;

import com.bank.dcp.monitoring.measurement.entity.ServerMetricSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface ServerMetricSnapshotRepository extends JpaRepository<ServerMetricSnapshot, UUID> {
}
