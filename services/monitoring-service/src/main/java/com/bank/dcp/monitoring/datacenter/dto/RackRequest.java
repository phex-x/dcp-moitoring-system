package com.bank.dcp.monitoring.datacenter.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record RackRequest(
        UUID dataCenterId,
        String code,
        String name,
        BigDecimal powerCapacityW,
        String status
) {
}
