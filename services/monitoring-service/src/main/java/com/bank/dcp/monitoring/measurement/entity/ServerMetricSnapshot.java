package com.bank.dcp.monitoring.measurement.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.UUID;
import com.bank.dcp.monitoring.server.entity.Server;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="server_metric_snapshots")
public class ServerMetricSnapshot {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="captured_at", nullable = false)
    private Instant capturedAt;
    @Column(name="cpu_load_pct")
    private BigDecimal cpuLoadPct;
    @Column(name="memory_usage_pct")
    private BigDecimal memoryUsagePct;
    @Column(name="temperature_c")
    private BigDecimal temperatureC;
    @Column(name="network_in_mbps")
    private BigDecimal networkInMbps;
    @Column(name="network_out_mbps")
    private BigDecimal networkOutMbps;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="server_id", nullable = false)
    private Server server;
}
