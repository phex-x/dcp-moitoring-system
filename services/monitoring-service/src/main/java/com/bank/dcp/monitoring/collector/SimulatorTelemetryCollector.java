package com.bank.dcp.monitoring.collector;

import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.Constants;
import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import com.bank.dcp.monitoring.common.enums.MeasurementQualityStatus;
import com.bank.dcp.monitoring.common.enums.MeasurementSourceType;
import com.bank.dcp.monitoring.measurement.entity.EnergyMeasurement;
import com.bank.dcp.monitoring.server.entity.Server;
import com.bank.dcp.monitoring.server.repository.ServerRepository;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class SimulatorTelemetryCollector implements TelemetryCollector {
    private final ServerRepository serverRepository;

    @Override
    public List<EnergyMeasurement> collect(MeasurementSource source, int samplesPerServer) {
        if (source.getType() != MeasurementSourceType.SIMULATOR) {
            throw new UnsupportedOperationException(Constants.Messages.UNSUPPORTED_SIMULATOR_SOURCE);
        }

        List<Server> servers = serverRepository.getAllByDataCenterIdAndStatus(
                source.getDataCenter().getId(),
                InfrastructureStatus.ACTIVE
        );
        int intervalSeconds = Objects.isNull(source.getPollIntervalSec())
                ? Constants.Simulator.DEFAULT_INTERVAL_SECONDS
                : source.getPollIntervalSec();

        return servers.stream()
                .flatMap(server -> IntStream.range(0, samplesPerServer)
                        .mapToObj(sample -> createMeasurement(source, server, intervalSeconds, sample)))
                .toList();
    }

    private EnergyMeasurement createMeasurement(
            MeasurementSource source,
            Server server,
            int intervalSeconds,
            int sample
    ) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        BigDecimal powerW = decimal(random.nextDouble(
                Constants.Simulator.MIN_POWER_W,
                Constants.Simulator.MAX_POWER_W
        ), Constants.Simulator.POWER_SCALE);
        BigDecimal energyKwh = powerW
                .multiply(BigDecimal.valueOf(intervalSeconds))
                .divide(
                        BigDecimal.valueOf(Constants.Simulator.ENERGY_CONVERSION_MILLISECONDS_PER_HOUR),
                        Constants.Simulator.ENERGY_SCALE,
                        RoundingMode.HALF_UP
                );

        return EnergyMeasurement.builder()
                .server(server)
                .source(source)
                .measuredAt(Instant.now().minusSeconds((long) sample * intervalSeconds))
                .powerW(powerW)
                .energyIntervalKwh(energyKwh)
                .cpuLoadPct(decimal(random.nextDouble(
                        Constants.Simulator.MIN_CPU_LOAD_PCT,
                        Constants.Simulator.MAX_CPU_LOAD_PCT
                ), Constants.Simulator.METRIC_SCALE))
                .memoryUsagePct(decimal(random.nextDouble(
                        Constants.Simulator.MIN_MEMORY_USAGE_PCT,
                        Constants.Simulator.MAX_MEMORY_USAGE_PCT
                ), Constants.Simulator.METRIC_SCALE))
                .temperatureC(decimal(random.nextDouble(
                        Constants.Simulator.MIN_TEMPERATURE_C,
                        Constants.Simulator.MAX_TEMPERATURE_C
                ), Constants.Simulator.METRIC_SCALE))
                .qualityStatus(MeasurementQualityStatus.VALID)
                .build();
    }

    private BigDecimal decimal(double value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }
}
