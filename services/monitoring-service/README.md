# Monitoring Service

Отдельное Spring Boot-приложение для инфраструктуры ЦОД и энергетических измерений. Сервис владеет только базой `monitoring_db`; обмен с другими сервисами выполняется через API и Kafka.

## Структура кода

- `datacenter` — ЦОД и стойки.
- `server` — серверы.
- `measurement` — измерения, фильтры, агрегаты и временные ряды.
- `source` и `collector` — источники данных и сбор телеметрии.
- `kafka` — события и интеграция с брокером.
- `dashboard` — сводные read-запросы.
- `common` — общие типы и ошибки этого сервиса.

## Миграции

Liquibase changelog расположен в `src/main/resources/db/changelog/db.changelog-master.yaml`. У каждого микросервиса свой Liquibase changelog и собственная база; общий changelog или прямой запуск миграций одного сервиса в базе другого запрещены. Hibernate настроен на `ddl-auto: validate`, поэтому схему изменяют только миграции.

Параметры БД задаются через `MONITORING_DB_URL`, `MONITORING_DB_USERNAME` и `MONITORING_DB_PASSWORD`. Предметные таблицы будут вводиться последующими feature-ветками вместе с моделями и API.
