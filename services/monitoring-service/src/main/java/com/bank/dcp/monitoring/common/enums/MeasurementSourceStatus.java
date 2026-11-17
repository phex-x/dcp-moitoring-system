package com.bank.dcp.monitoring.common.enums;

public enum MeasurementSourceStatus {
    ACTIVE,
    INACTIVE,
    UNAVAILABLE;

    public boolean canTransitionTo(MeasurementSourceStatus nextStatus) {
        return switch (this) {
            case ACTIVE -> nextStatus == ACTIVE || nextStatus == INACTIVE || nextStatus == UNAVAILABLE;
            case INACTIVE -> nextStatus == INACTIVE || nextStatus == ACTIVE;
            case UNAVAILABLE -> nextStatus == UNAVAILABLE || nextStatus == ACTIVE || nextStatus == INACTIVE;
        };
    }
}
