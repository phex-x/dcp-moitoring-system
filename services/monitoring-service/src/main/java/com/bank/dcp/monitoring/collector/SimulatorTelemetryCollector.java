package com.bank.dcp.monitoring.collector;

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
    private static final int DEFAULT_INTERVAL_SECONDS = 60;
    private final ServerRepository serverRepository;

    @Override
    public List<EnergyMeasurement> collect(MeasurementSource source, int samplesPerServer) {
        if (source.getType() != MeasurementSourceType.SIMULATOR) {
            throw new UnsupportedOperationException("Only SIMULATOR sources are supported by this collector");
        }

        List<Server> servers = serverRepository.getAllByDataCenterIdAndStatus(
                source.getDataCenter().getId(),
                InfrastructureStatus.ACTIVE
        );
        int intervalSeconds = Objects.isNull(source.getPollIntervalSec())
                ? DEFAULT_INTERVAL_SECONDS
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
        BigDecimal powerW = decimal(random.nextDouble(120.0, 650.0), 3);
        BigDecimal energyKwh = powerW
                .multiply(BigDecimal.valueOf(intervalSeconds))
                .divide(BigDecimal.valueOf(3_600_000), 6, RoundingMode.HALF_UP);

        return EnergyMeasurement.builder()
                .server(server)
                .source(source)
                .measuredAt(Instant.now().minusSeconds((long) sample * intervalSeconds))
                .powerW(powerW)
                .energyIntervalKwh(energyKwh)
                .cpuLoadPct(decimal(random.nextDouble(5.0, 95.0), 2))
                .memoryUsagePct(decimal(random.nextDouble(10.0, 90.0), 2))
                .temperatureC(decimal(random.nextDouble(20.0, 75.0), 2))
                .qualityStatus(MeasurementQualityStatus.VALID)
                .build();
    }

    private BigDecimal decimal(double value, int scale) {
        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP);
    }
}
