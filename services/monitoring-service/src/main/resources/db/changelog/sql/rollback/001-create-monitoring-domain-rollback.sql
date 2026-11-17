-- Автор: Dmitriy Serov
-- Откат миграции 001-create-monitoring-domain.

DROP TABLE IF EXISTS outbox_events;
DROP TABLE IF EXISTS collector_jobs;
DROP TABLE IF EXISTS server_metric_snapshots;
DROP TABLE IF EXISTS energy_measurements;
DROP TABLE IF EXISTS measurement_sources;
DROP TABLE IF EXISTS servers;
DROP TABLE IF EXISTS racks;
DROP TABLE IF EXISTS data_centers;
