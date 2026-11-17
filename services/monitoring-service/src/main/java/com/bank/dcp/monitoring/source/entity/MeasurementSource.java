package com.bank.dcp.monitoring.source.entity;

import jakarta.persistence.*;
import com.bank.dcp.monitoring.common.enums.MeasurementSourceStatus;
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
@Table(name="measurement_sources")
public class MeasurementSource {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="name", length = 150, nullable = false)
    private String name;
    @Column(name="type", length = 32, nullable = false)
    private String type;
    @Column(name="endpoint", length = 500)
    private String endpoint;
    @Enumerated(EnumType.STRING)
    @Column(name="status", length = 20, nullable = false)
    private MeasurementSourceStatus status;
    @Column(name="poll_interval_sec")
    private Integer pollIntervalSec;
    @Column(name="last_success_at")
    private Instant lastSuccessAt;
    @Column(name="last_error_at")
    private Instant lastErrorAt;
    @Column(name="last_error", columnDefinition = "text")
    private String lastError;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="data_center_id", nullable = false)
    private DataCenter dataCenter;
}
