# Статусы Monitoring Service

Статусы в REST DTO передаются строковыми значениями enum. Неизвестные значения отклоняются Jakarta Validation/Jackson как некорректный запрос. Статус при создании обязателен для ЦОД, стоек, серверов и источников. В PATCH отсутствие статуса означает «оставить без изменений».

## Инфраструктура

`InfrastructureStatus`: `ACTIVE`, `INACTIVE`, `MAINTENANCE`, `DECOMMISSIONED`.

| Текущий статус | Допустимые следующие статусы |
| --- | --- |
| `ACTIVE` | `ACTIVE`, `INACTIVE`, `MAINTENANCE`, `DECOMMISSIONED` |
| `INACTIVE` | `INACTIVE`, `ACTIVE`, `DECOMMISSIONED` |
| `MAINTENANCE` | `MAINTENANCE`, `ACTIVE`, `INACTIVE`, `DECOMMISSIONED` |
| `DECOMMISSIONED` | только `DECOMMISSIONED` |

`DELETE` для ЦОД, стоек и серверов переводит объект в `INACTIVE`. Перевод в `DECOMMISSIONED` терминальный. Недопустимые переходы возвращают HTTP 422.

## Источники измерений

`MeasurementSourceStatus`: `ACTIVE`, `INACTIVE`, `UNAVAILABLE`.

| Текущий статус | Допустимые следующие статусы |
| --- | --- |
| `ACTIVE` | `ACTIVE`, `INACTIVE`, `UNAVAILABLE` |
| `INACTIVE` | `INACTIVE`, `ACTIVE` |
| `UNAVAILABLE` | `UNAVAILABLE`, `ACTIVE`, `INACTIVE` |

Успешная проверка или синхронизация помечает источник как `ACTIVE`; неуспешная проверка/синхронизация переводит активный источник в `UNAVAILABLE`. `DELETE` переводит источник в `INACTIVE`. Неактивный источник не запускает синхронизацию.

## Задания, качество и outbox

- `CollectorJobStatus`: `PENDING` → `RUNNING` → `SUCCEEDED`, `PARTIAL_SUCCESS` или `FAILED`. Завершённое задание не запускается повторно; новый запуск создаёт новую запись.
- `MeasurementQualityStatus`: `VALID`, `WARNING`, `INVALID`. Это оценка измерения, а не переходный статус.
- `OutboxEventStatus`: `PENDING`, `PUBLISHED`, `FAILED`. Переходы и повторы доставки управляются publisher-ом; статус не задаётся внешним REST-клиентом.

## Валидация измерений

- Мощность и энергия: неотрицательные значения.
- CPU и память: от 0 до 100 процентов включительно.
- Температура: от −50 до 150 °C включительно.
- Энергетические измерения и snapshots датируются не позже текущего времени.
- Временной фильтр требует `from <= to`; мощность требует `minPower <= maxPower`.
- Интервал опроса источника, если задан, составляет от 1 до 86400 секунд.
- Целевой PUE ограничен диапазоном от 1 до 10.
