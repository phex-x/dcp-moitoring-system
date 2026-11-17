package com.bank.dcp.monitoring.measurement.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.UUID;
import com.bank.dcp.monitoring.server.entity.Server;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="energy_measurements")
public class EnergyMeasurement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @PrePersist
    void onCreate() { createdAt = Instant.now(); }
    @Column(name="measured_at", nullable = false)
    private Instant measuredAt;
    @Column(name="power_w")
    private BigDecimal powerW;
    @Column(name="energy_interval_kwh")
    private BigDecimal energyIntervalKwh;
    @Column(name="cpu_load_pct")
    private BigDecimal cpuLoadPct;
    @Column(name="memory_usage_pct")
    private BigDecimal memoryUsagePct;
    @Column(name="temperature_c")
    private BigDecimal temperatureC;
    @Column(name="quality_status", length = 20)
    private String qualityStatus;
    @Column(name="checksum", length = 64)
    private String checksum;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="server_id", nullable = false)
    private Server server;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="source_id", nullable = false)
    private MeasurementSource source;
}
