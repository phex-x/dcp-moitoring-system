package com.bank.dcp.monitoring.collector.repository;

import com.bank.dcp.monitoring.collector.entity.CollectorJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CollectorJobRepository extends JpaRepository<CollectorJob, UUID> {
    Page<CollectorJob> getAllBySource_Id(UUID sourceId, Pageable pageable);
}
