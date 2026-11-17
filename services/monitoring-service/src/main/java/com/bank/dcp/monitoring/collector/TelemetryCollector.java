package com.bank.dcp.monitoring.collector;

import com.bank.dcp.monitoring.measurement.entity.EnergyMeasurement;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import java.util.List;

public interface TelemetryCollector {
    List<EnergyMeasurement> collect(MeasurementSource source, int samplesPerServer);
}
