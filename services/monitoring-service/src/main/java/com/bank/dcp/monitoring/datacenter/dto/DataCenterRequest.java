package com.bank.dcp.monitoring.datacenter.dto;

import java.math.BigDecimal;

public record DataCenterRequest(
        String code,
        String name,
        String location,
        String timezone,
        BigDecimal pueTarget,
        String status
) {
}
