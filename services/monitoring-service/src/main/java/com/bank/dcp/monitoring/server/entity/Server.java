package com.bank.dcp.monitoring.server.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.UUID;
import com.bank.dcp.monitoring.datacenter.entity.Rack;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="servers")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @PrePersist
    void onCreate() {
        Instant now = Instant.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    void onUpdate() { updatedAt = Instant.now(); }
    @Column(name="hostname", length = 120, nullable = false)
    private String hostname;
    @Column(name="asset_tag", length = 64, nullable = false)
    private String assetTag;
    @Column(name="vendor", length = 100)
    private String vendor;
    @Column(name="model", length = 100)
    private String model;
    @Column(name="cpu_cores")
    private Integer cpuCores;
    @Column(name="ram_gb")
    private BigDecimal ramGb;
    @Column(name="rated_power_w")
    private BigDecimal ratedPowerW;
    @Column(name="status", length = 20, nullable = false)
    private String status;
    @Column(name="installed_at")
    private Instant installedAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="rack_id", nullable = false)
    private Rack rack;
}
