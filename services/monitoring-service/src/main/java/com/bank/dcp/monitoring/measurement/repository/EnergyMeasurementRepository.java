package com.bank.dcp.monitoring.measurement.repository;

import com.bank.dcp.monitoring.measurement.entity.EnergyMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;
import java.util.UUID;

public interface EnergyMeasurementRepository extends JpaRepository<EnergyMeasurement, UUID>, JpaSpecificationExecutor<EnergyMeasurement> {
    Optional<EnergyMeasurement> getEnergyMeasurementById(UUID id);
}
