-- Автор: Dmitriy Serov
-- Базовая схема Monitoring Service.

CREATE TABLE data_centers (
  id UUID PRIMARY KEY,
  code VARCHAR(32) NOT NULL UNIQUE,
  name VARCHAR(150) NOT NULL,
  location VARCHAR(255),
  timezone VARCHAR(64),
  pue_target NUMERIC(6,3),
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  updated_at TIMESTAMPTZ NOT NULL
);
CREATE TABLE racks (
  id UUID PRIMARY KEY,
  data_center_id UUID NOT NULL REFERENCES data_centers(id),
  code VARCHAR(32) NOT NULL,
  name VARCHAR(150) NOT NULL,
  power_capacity_w NUMERIC(14,3),
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  updated_at TIMESTAMPTZ NOT NULL,
  CONSTRAINT uq_racks_dc_code UNIQUE (data_center_id, code)
);
CREATE TABLE servers (
  id UUID PRIMARY KEY,
  rack_id UUID NOT NULL REFERENCES racks(id),
  hostname VARCHAR(120) NOT NULL UNIQUE,
  asset_tag VARCHAR(64) NOT NULL UNIQUE,
  vendor VARCHAR(100),
  model VARCHAR(100),
  cpu_cores INTEGER,
  ram_gb NUMERIC(10,2),
  rated_power_w NUMERIC(12,3),
  status VARCHAR(20) NOT NULL,
  installed_at TIMESTAMPTZ,
  created_at TIMESTAMPTZ NOT NULL,
  updated_at TIMESTAMPTZ NOT NULL
);
CREATE TABLE measurement_sources (
  id UUID PRIMARY KEY,
  data_center_id UUID NOT NULL REFERENCES data_centers(id),
  name VARCHAR(150) NOT NULL,
  type VARCHAR(32) NOT NULL,
  endpoint VARCHAR(500),
  status VARCHAR(20) NOT NULL,
  poll_interval_sec INTEGER,
  last_success_at TIMESTAMPTZ,
  last_error_at TIMESTAMPTZ,
  last_error TEXT
);
CREATE TABLE energy_measurements (
  id UUID PRIMARY KEY,
  server_id UUID NOT NULL REFERENCES servers(id),
  source_id UUID NOT NULL REFERENCES measurement_sources(id),
  measured_at TIMESTAMPTZ NOT NULL,
  power_w NUMERIC(14,3) NOT NULL,
  energy_interval_kwh NUMERIC(14,6),
  cpu_load_pct NUMERIC(5,2),
  memory_usage_pct NUMERIC(5,2),
  temperature_c NUMERIC(5,2),
  quality_status VARCHAR(20) NOT NULL,
  checksum CHAR(64),
  created_at TIMESTAMPTZ NOT NULL
);
CREATE INDEX idx_energy_measurements_server_time ON energy_measurements(server_id, measured_at DESC);
CREATE INDEX idx_energy_measurements_source_time ON energy_measurements(source_id, measured_at DESC);
CREATE INDEX idx_energy_measurements_quality ON energy_measurements(quality_status);
CREATE TABLE server_metric_snapshots (
  id UUID PRIMARY KEY,
  server_id UUID NOT NULL REFERENCES servers(id),
  captured_at TIMESTAMPTZ NOT NULL,
  cpu_load_pct NUMERIC(5,2),
  memory_usage_pct NUMERIC(5,2),
  temperature_c NUMERIC(5,2),
  network_in_mbps NUMERIC(14,3),
  network_out_mbps NUMERIC(14,3)
);
CREATE INDEX idx_server_metric_snapshots_server_time ON server_metric_snapshots(server_id, captured_at DESC);
CREATE TABLE collector_jobs (
  id UUID PRIMARY KEY,
  source_id UUID NOT NULL REFERENCES measurement_sources(id),
  started_at TIMESTAMPTZ NOT NULL,
  finished_at TIMESTAMPTZ,
  records_received BIGINT NOT NULL DEFAULT 0,
  records_saved BIGINT NOT NULL DEFAULT 0,
  records_rejected BIGINT NOT NULL DEFAULT 0,
  status VARCHAR(20) NOT NULL,
  error_message TEXT
);
CREATE INDEX idx_collector_jobs_source_started ON collector_jobs(source_id, started_at DESC);
CREATE TABLE outbox_events (
  id UUID PRIMARY KEY,
  aggregate_type VARCHAR(64) NOT NULL,
  aggregate_id UUID NOT NULL,
  event_type VARCHAR(128) NOT NULL,
  payload JSONB NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  published_at TIMESTAMPTZ,
  attempts INTEGER NOT NULL DEFAULT 0,
  status VARCHAR(20) NOT NULL
);
CREATE INDEX idx_outbox_events_status_created ON outbox_events(status, created_at);
