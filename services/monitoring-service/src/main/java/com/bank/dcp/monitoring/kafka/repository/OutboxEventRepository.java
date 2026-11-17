package com.bank.dcp.monitoring.kafka.repository;

import com.bank.dcp.monitoring.kafka.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {
}
