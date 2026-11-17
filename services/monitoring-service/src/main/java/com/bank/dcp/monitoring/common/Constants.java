package com.bank.dcp.monitoring.common;

public final class Constants {
    public static final class Pagination {
        public static final int MIN_PAGE_SIZE = 1;
        public static final int MAX_PAGE_SIZE = 100;
        public static final int DEFAULT_PAGE_SIZE = 20;
        public static final int MEASUREMENT_PAGE_SIZE = 50;
        public static final int SERIES_PAGE_SIZE = 100;
        public static final int MAX_BULK_SIZE = 1000;

        private Pagination() {
        }
    }

    public static final class Validation {
        public static final int MAX_CODE_LENGTH = 32;
        public static final int MAX_NAME_LENGTH = 150;
        public static final int MAX_LOCATION_LENGTH = 255;
        public static final int MAX_TIMEZONE_LENGTH = 64;
        public static final int MAX_HOSTNAME_LENGTH = 120;
        public static final int MAX_ASSET_TAG_LENGTH = 64;
        public static final int MAX_VENDOR_LENGTH = 100;
        public static final int MAX_MODEL_LENGTH = 100;
        public static final int MAX_DESCRIPTION_LENGTH = 500;
        public static final int MAX_POLL_INTERVAL_SECONDS = 86400;
        public static final int MIN_POLL_INTERVAL_SECONDS = 1;
        public static final int MIN_COLLECTOR_SAMPLES = 1;
        public static final int MAX_COLLECTOR_SAMPLES = 100;
        public static final int DEFAULT_SAMPLES_PER_SERVER = 1;
        public static final int MIN_NON_NEGATIVE_COUNT = 0;
        public static final int SHA_256_LENGTH = 64;
        public static final String MIN_NON_NEGATIVE = "0.0";
        public static final String MAX_PERCENT = "100.0";
        public static final String MIN_TEMPERATURE = "-50.0";
        public static final String MAX_TEMPERATURE = "150.0";
        public static final String MIN_PUE = "1.0";
        public static final String MAX_PUE = "10.0";
        public static final String SHA_256_PATTERN = "[a-fA-F0-9]{64}";

        private Validation() {
        }
    }

    public static final class Simulator {
        public static final int DEFAULT_INTERVAL_SECONDS = 60;
        public static final int ENERGY_CONVERSION_MILLISECONDS_PER_HOUR = 3_600_000;
        public static final int POWER_SCALE = 3;
        public static final int ENERGY_SCALE = 6;
        public static final int METRIC_SCALE = 2;
        public static final double MIN_POWER_W = 120.0;
        public static final double MAX_POWER_W = 650.0;
        public static final double MIN_CPU_LOAD_PCT = 5.0;
        public static final double MAX_CPU_LOAD_PCT = 95.0;
        public static final double MIN_MEMORY_USAGE_PCT = 10.0;
        public static final double MAX_MEMORY_USAGE_PCT = 90.0;
        public static final double MIN_TEMPERATURE_C = 20.0;
        public static final double MAX_TEMPERATURE_C = 75.0;

        private Simulator() {
        }
    }

    public static final class Api {
        public static final String DATA_CENTERS = "/api/v1/data-centers";
        public static final String RACKS = "/api/v1/racks";
        public static final String SERVERS = "/api/v1/servers";
        public static final String SOURCES = "/api/v1/sources";
        public static final String MEASUREMENTS = "/api/v1/measurements";
        public static final String COLLECTOR_JOBS = "/api/v1/collector/jobs";
        public static final String SERIES = "/series";
        public static final String BULK = "/bulk";
        public static final String RESOURCE_ID = "/{id}";
        public static final String SOURCE_TEST = "/{id}/test";
        public static final String SOURCE_SYNC = "/{id}/sync";

        private Api() {
        }
    }

    public static final class ResourceNames {
        public static final String DATA_CENTER = "Data center";
        public static final String RACK = "Rack";
        public static final String SERVER = "Server";
        public static final String SOURCE = "Measurement source";
        public static final String MEASUREMENT = "Energy measurement";

        private ResourceNames() {
        }
    }

    public static final class Messages {
        public static final String INVALID_INFRASTRUCTURE_STATUS_TRANSITION = "Invalid InfrastructureStatus transition";
        public static final String DECOMMISSIONED_INFRASTRUCTURE_DEACTIVATION = "Decommissioned infrastructure cannot be deactivated";
        public static final String INVALID_SOURCE_STATUS_TRANSITION = "Invalid MeasurementSourceStatus transition";
        public static final String SOURCE_CANNOT_BE_INACTIVE = "Source cannot transition to INACTIVE";
        public static final String INACTIVE_SOURCE_SYNCHRONIZATION = "Inactive source cannot be synchronized";
        public static final String SIMULATOR_SOURCE_AVAILABLE = "Simulator source is available";
        public static final String SOURCE_UNAVAILABLE = "Source is inactive or has no registered collector";
        public static final String UNSUPPORTED_SIMULATOR_SOURCE = "Only SIMULATOR sources are supported by this collector";
        public static final String UNSUPPORTED_SORT_FIELD = "Unsupported sort field: ";
        public static final String INVALID_PAGE_SIZE = "Page size must be between 1 and 100";
        public static final String INVALID_TIME_RANGE = "The from timestamp must be before or equal to the to timestamp";
        public static final String INVALID_POWER_RANGE = "minPower must be less than or equal to maxPower";
        public static final String VALIDATION_FAILED = "error.validation.title";
        public static final String VALIDATION_ERROR = "VALIDATION_ERROR";
        public static final String NOT_FOUND = "NOT_FOUND";
        public static final String BUSINESS_RULE_VIOLATION = "BUSINESS_RULE_VIOLATION";
        public static final String RESOURCE_CONFLICT = "RESOURCE_CONFLICT";
        public static final String DUPLICATE_RESOURCE = "error.resource.conflict";

        private Messages() {
        }
    }

    public static final class SortFields {
        public static final String NAME = "name";
        public static final String CODE = "code";
        public static final String LOCATION = "location";
        public static final String STATUS = "status";
        public static final String CREATED_AT = "createdAt";
        public static final String UPDATED_AT = "updatedAt";
        public static final String POLL_INTERVAL_SEC = "pollIntervalSec";
        public static final String TYPE = "type";
        public static final String HOSTNAME = "hostname";
        public static final String ASSET_TAG = "assetTag";
        public static final String INSTALLED_AT = "installedAt";
        public static final String MEASURED_AT = "measuredAt";
        public static final String POWER_W = "powerW";
        public static final String ENERGY_INTERVAL_KWH = "energyIntervalKwh";
        public static final String CPU_LOAD_PCT = "cpuLoadPct";
        public static final String QUALITY_STATUS = "qualityStatus";
        public static final String STARTED_AT = "startedAt";
        public static final String FINISHED_AT = "finishedAt";
        public static final String ID = "id";

        private SortFields() {
        }
    }

    public static final class JpaProperties {
        public static final String SERVER = "server";
        public static final String RACK = "rack";
        public static final String DATA_CENTER = "dataCenter";
        public static final String SOURCE = "source";
        public static final String ID = "id";

        private JpaProperties() {
        }
    }

    private Constants() {
    }
}
