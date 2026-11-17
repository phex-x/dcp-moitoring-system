package com.bank.dcp.monitoring.datacenter.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.math.BigDecimal;
import java.util.UUID;

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
@Table(name="data_centers")
public class DataCenter {
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
    @Column(name="code", length = 32, nullable = false)
    private String code;
    @Column(name="name", length = 150, nullable = false)
    private String name;
    @Column(name="location", length = 255)
    private String location;
    @Column(name="timezone", length = 64)
    private String timezone;
    @Column(name="pue_target")
    private BigDecimal pueTarget;
    @Column(name="status", length = 20, nullable = false)
    private String status;
}
