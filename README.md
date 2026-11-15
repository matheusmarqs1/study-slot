# Study Slot

A study room reservation backend built with Spring Boot. Early stage: the database schema is defined and basic CRUD endpoints are available for rooms, users, and reservations.

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

Each resource (`rooms`, `users`, `reservations`) exposes the same CRUD operations under `/api/v1/{resource}`:

| Method | Path                         | Description              | Success |
|--------|------------------------------|--------------------------|---------|
| GET    | `/api/v1/{resource}`         | List all                 | 200     |
| GET    | `/api/v1/{resource}/{id}`    | Get by id                | 200     |
| POST   | `/api/v1/{resource}`         | Create                   | 201     |
| PUT    | `/api/v1/{resource}/{id}`    | Update                   | 200     |
| DELETE | `/api/v1/{resource}/{id}`    | Delete                   | 204     |

`POST` returns a `Location` header pointing to the new resource.

Creating a reservation takes the user and room by id:

```json
{
  "user": { "id": 1 },
  "room": { "id": 1 },
  "startsAt": "2026-11-20T10:00:00Z",
  "endsAt": "2026-11-20T11:00:00Z",
  "status": "ACTIVE"
}
```

Current limitations: the API exposes entities directly (no DTOs yet), and validation and error handling are not in place, so invalid input or missing ids return `500`.

## Database schema

Defined in `V1__criar_tabelas.sql`:

- `users`: name, unique email
- `room`: name, capacity (> 0), active flag
- `reservation`: user, room, status (`ACTIVE`, `CANCELLED`, `COMPLETED`), start/end time (end must be after start)
