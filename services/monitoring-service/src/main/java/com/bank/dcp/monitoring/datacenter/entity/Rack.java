package com.bank.dcp.monitoring.datacenter.entity;

import jakarta.persistence.*;
import com.bank.dcp.monitoring.common.enums.InfrastructureStatus;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.UUID;
import com.bank.dcp.monitoring.datacenter.entity.DataCenter;

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
@Table(name="racks")
public class Rack {
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
    void onUpdate() {
        updatedAt = Instant.now();
    }
    @Column(name="code", length = 32, nullable = false)
    private String code;
    @Column(name="name", length = 150, nullable = false)
    private String name;
    @Column(name="power_capacity_w")
    private BigDecimal powerCapacityW;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private InfrastructureStatus status;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="data_center_id", nullable = false)
    private DataCenter dataCenter;
}
