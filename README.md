# KaseOrchestrator

> Part of the [Kase](https://github.com/Avi-Rana-1718/Kase) project

This service handles case creation, task pipeline orchestration, and async task execution for the broader Kase ecosystem.

## Features

- Case creation and tracking with type/subtype classification
- Configurable task pipelines triggered on case events
- Async pipeline execution via Kafka
- Plugin-style `TaskDefinition` interface for custom task types
- Built-in `SendCommunicationTask` for email/SMS/push notifications
- Multi-tenant support via organization headers

## Tech Stack

- **Java 21**
- **Spring Boot 3.2.0** (Web, Data JPA)
- **PostgreSQL**
- **Flyway** (Database migrations)
- **Apache Kafka** (Async task messaging)
- **Lombok**
- **Maven**

## Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL 14+
- Apache Kafka broker

## API Endpoints

### Cases

| Method | Endpoint | Description | Headers |
|--------|----------|-------------|---------|
| POST | `/case/caseCreation` | Create a new case | `X-User-Details` |
| GET | `/case/details` | Get case by case number | `X-User-Details` |
| POST | `/case/upsertType` | Create or update a case type | `X-User-Details` |
| GET | `/case/types` | List active types for org | `X-User-Details` |
| GET | `/case/subtypes` | List active subtypes for org | `X-User-Details` |

### Tasks

| Method | Endpoint | Description | Headers |
|--------|----------|-------------|---------|
| POST | `/task` | Register a new task definition | `X-User-Details` |
| GET | `/task` | List all registered tasks | `X-User-Details` |

### Pipelines

| Method | Endpoint | Description | Headers |
|--------|----------|-------------|---------|
| POST | `/pipelines` | Create a pipeline with ordered steps | `X-User-Details` |
| GET | `/pipelines` | List pipelines for org | `X-User-Details` |
| GET | `/pipelines/{id}` | Get pipeline details | `X-User-Details` |

## Project Structure

```
src/main/java/com/avirana/
├── KaseOrchestrator.java          # Application entry point
├── config/                        # Web config, exception handler, converters
├── constants/                     # Header names, Kafka topic names
├── controller/                    # REST controllers (Case, Task, Pipeline)
├── dto/                           # Data transfer objects
├── entity/                        # JPA entities
├── enums/                         # CaseStatus, TaskStatus, TaskType, OwnerType
├── interceptor/                   # Feature gating (stub)
├── messaging/                     # Kafka consumer/producer + event model
├── repository/                    # Data repositories
├── service/                       # Business logic services
├── service/task/definition/       # TaskDefinition interface + implementations
├── service/task/executor/         # TaskManager — orchestrates step execution
└── util/                          # Utility classes
```

## License

This project is proprietary.