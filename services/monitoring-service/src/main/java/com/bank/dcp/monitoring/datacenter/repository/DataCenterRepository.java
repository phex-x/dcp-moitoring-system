package com.bank.dcp.monitoring.datacenter.repository;

import com.bank.dcp.monitoring.datacenter.entity.DataCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DataCenterRepository extends JpaRepository<DataCenter, UUID> {
}
