package com.bank.dcp.monitoring.collector.entity;

import jakarta.persistence.*;
import com.bank.dcp.monitoring.common.enums.CollectorJobStatus;
import java.time.Instant;
import java.util.UUID;
import com.bank.dcp.monitoring.source.entity.MeasurementSource;

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
@Table(name="collector_jobs")
public class CollectorJob {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name="started_at", nullable = false)
    private Instant startedAt;
    @Column(name="finished_at")
    private Instant finishedAt;
    @Column(name="records_received")
    private Long recordsReceived;
    @Column(name="records_saved")
    private Long recordsSaved;
    @Column(name="records_rejected")
    private Long recordsRejected;
    @Enumerated(EnumType.STRING)
    @Column(name="status", length = 20, nullable = false)
    private CollectorJobStatus status;
    @Column(name="error_message", columnDefinition = "text")
    private String errorMessage;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="source_id", nullable = false)
    private MeasurementSource source;
}
