package com.bank.dcp.monitoring.collector.repository;

import com.bank.dcp.monitoring.collector.entity.CollectorJob;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.UUID;

public interface CollectorJobRepository extends JpaRepository<CollectorJob, UUID> {
    @Query("select collectorJob from CollectorJob collectorJob where collectorJob.source.id = :sourceId")
    Page<CollectorJob> getAllBySourceId(@Param("sourceId") UUID sourceId, Pageable pageable);
}
