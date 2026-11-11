# Study Slot

A study room reservation backend built with Spring Boot. Early stage: the database schema is defined and a read-only rooms endpoint is available.

## Stack

- Java 21
- Spring Boot 4.1.2 (Web MVC, Data JPA, Validation)
- PostgreSQL 18
- Flyway (schema migrations)
- Lombok
- Maven

## Getting started

Start the database:

```bash
docker compose up -d
```

Run the application (from the `study-slot/` directory):

```bash
./mvnw spring-boot:run
```

Flyway applies the migrations on startup. Connection settings live in `study-slot/src/main/resources/application.properties`.

## API

| Method | Path            | Description        |
|--------|-----------------|--------------------|
| GET    | `/api/v1/rooms` | List all rooms     |

## Database schema

Defined in `V1__criar_tabelas.sql`:

- `users`: name, unique email
- `room`: name, capacity (> 0), active flag
- `reservation`: user, room, status (`ACTIVE`, `CANCELLED`, `COMPLETED`), start/end time (end must be after start)
