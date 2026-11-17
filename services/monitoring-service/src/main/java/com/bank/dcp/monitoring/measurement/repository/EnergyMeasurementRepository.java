package com.bank.dcp.monitoring.measurement.repository;

import com.bank.dcp.monitoring.measurement.entity.EnergyMeasurement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface EnergyMeasurementRepository extends JpaRepository<EnergyMeasurement, UUID> {
}
