# DCP Energy Monitoring System

Начальный каркас монорепозитория: три Spring Boot-сервиса, API Gateway, React-клиент и локальная инфраструктура.

## Сервисы

- `auth-service` — аутентификация и роли.
- `monitoring-service` — инфраструктура ЦОД и измерения.
- `ecology-service` — каталог данных и экологический анализ.
- `gateway/api-gateway` — единая точка входа.

Сервисы запускаются независимо; бизнес-функции пока не реализованы.

## Локальная инфраструктура

Скопируйте `.env.example` в `.env`, задайте локальный пароль и запустите `docker compose up -d`. Compose поднимает PostgreSQL с базами `auth_db`, `monitoring_db`, `ecology_db`, Kafka и Redis.

Java-сервисы используют Spring Boot 3.5.16, Java 21 и Maven. Запуск отдельного сервиса: `mvn -pl services/auth-service spring-boot:run` (аналогично `monitoring-service`, `ecology-service`; gateway находится в `gateway/api-gateway`).

Actuator health доступен по `/actuator/health`. Реализация REST API, безопасности, миграций, frontend и интеграционных контрактов будет добавляться отдельными этапами.
