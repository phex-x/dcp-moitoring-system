package com.bank.dcp.monitoring.common.enums;

public enum InfrastructureStatus {
    ACTIVE,
    INACTIVE,
    MAINTENANCE,
    DECOMMISSIONED;

    public boolean canTransitionTo(InfrastructureStatus nextStatus) {
        return switch (this) {
            case ACTIVE -> nextStatus == ACTIVE || nextStatus == INACTIVE || nextStatus == MAINTENANCE || nextStatus == DECOMMISSIONED;
            case INACTIVE -> nextStatus == INACTIVE || nextStatus == ACTIVE || nextStatus == DECOMMISSIONED;
            case MAINTENANCE -> nextStatus == MAINTENANCE || nextStatus == ACTIVE || nextStatus == INACTIVE || nextStatus == DECOMMISSIONED;
            case DECOMMISSIONED -> nextStatus == DECOMMISSIONED;
        };
    }
}
