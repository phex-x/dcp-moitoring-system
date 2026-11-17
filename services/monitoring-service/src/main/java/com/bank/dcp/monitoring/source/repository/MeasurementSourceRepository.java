package com.bank.dcp.monitoring.source.repository;

import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.Optional;

public interface MeasurementSourceRepository extends JpaRepository<MeasurementSource, UUID> {
    Optional<MeasurementSource> getMeasurementSourceById(UUID id);
}
