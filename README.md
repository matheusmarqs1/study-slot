# Study Slot

A study room reservation backend built with Spring Boot. Early stage: the database schema is defined and CRUD endpoints with DTOs, request validation, global error handling and an overlapping-reservation rule are available for rooms, users, and reservations.

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

All routes are under `/api/v1`. `POST` returns a `Location` header pointing to the new resource.

### Rooms

| Method | Path                  | Description    | Success |
|--------|-----------------------|----------------|---------|
| GET    | `/api/v1/rooms`       | List all rooms | 200     |
| GET    | `/api/v1/rooms/{id}`  | Get a room     | 200     |
| POST   | `/api/v1/rooms`       | Create a room  | 201     |
| PUT    | `/api/v1/rooms/{id}`  | Update a room  | 200     |
| DELETE | `/api/v1/rooms/{id}`  | Delete a room  | 204     |

### Users

| Method | Path                  | Description    | Success |
|--------|-----------------------|----------------|---------|
| GET    | `/api/v1/users`       | List all users | 200     |
| GET    | `/api/v1/users/{id}`  | Get a user     | 200     |
| POST   | `/api/v1/users`       | Create a user  | 201     |
| PUT    | `/api/v1/users/{id}`  | Update a user  | 200     |
| DELETE | `/api/v1/users/{id}`  | Delete a user  | 204     |

### Reservations

| Method | Path                          | Description            | Success |
|--------|-------------------------------|------------------------|---------|
| GET    | `/api/v1/reservations`        | List all reservations  | 200     |
| GET    | `/api/v1/reservations/{id}`   | Get a reservation      | 200     |
| POST   | `/api/v1/reservations`        | Create a reservation   | 201     |
| PUT    | `/api/v1/reservations/{id}`   | Update a reservation   | 200     |
| DELETE | `/api/v1/reservations/{id}`   | Delete a reservation   | 204     |

### Request examples

Request and response bodies are DTOs, and request bodies are validated with Bean Validation (invalid input returns `400`, see [Error handling](#error-handling)).

#### Create a room

`active` is not sent on create: new rooms are always active.

```json
{ "name": "Study Room 01", "capacity": 6 }
```

#### Update a room

```json
{ "name": "Study Room 01", "capacity": 8, "active": false }
```

#### Create a user

```json
{ "name": "Maria Souza", "email": "maria.souza@email.com" }
```

#### Create a reservation

The user and room are sent by id. `startsAt` must be in the future and `endsAt` must be after `startsAt`. New reservations start as `ACTIVE`.

```json
{
  "userId": 1,
  "roomId": 1,
  "startsAt": "2026-12-05T10:00:00Z",
  "endsAt": "2026-12-05T11:00:00Z"
}
```

Response:

```json
{
  "id": 1,
  "user": { "id": 1, "name": "Maria Souza" },
  "room": { "id": 1, "name": "Study Room 01" },
  "startsAt": "2026-12-05T10:00:00Z",
  "endsAt": "2026-12-05T11:00:00Z",
  "status": "ACTIVE",
  "createdAt": "2026-11-16T15:00:00Z"
}
```

`PUT /api/v1/reservations/{id}` only reschedules: it accepts `startsAt` and `endsAt`. The status is not editable through it.

### Business rules

- A room cannot have two overlapping `ACTIVE` reservations. This is checked on create and on reschedule (a reservation never conflicts with itself).
- Back-to-back reservations are allowed: one ending at 11:00 does not conflict with one starting at 11:00.
- `CANCELLED` reservations do not block the room.
- The overlap check happens in the service and is not safe under concurrent requests yet.

### Error handling

Errors share one JSON format. `errors` is only present for validation failures.

```json
{
  "timestamp": "2026-11-21T20:45:53Z",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/v1/rooms",
  "errors": [
    { "field": "capacity", "message": "Room capacity must be at least 1" }
  ]
}
```

| Status | When                                                                 | `message`                          |
|--------|----------------------------------------------------------------------|------------------------------------|
| 400    | Request body fails validation                                        | `Validation failed` (plus `errors`) |
| 400    | Malformed JSON, wrong types or invalid enum/date format              | `Malformed JSON request`           |
| 404    | Room, user or reservation id does not exist                          | `Room with id 9999 not found`      |
| 409    | Duplicate e-mail, or deleting a room or user that has reservations   | `Data integrity violation`         |
| 409    | Reservation overlaps an active reservation in the same room          | `The room is already reserved for the given time range` |

### Current limitations

- Cancellation (with a minimum notice period) is not implemented yet.
- Unexpected server errors are not handled globally and fall back to Spring's default error body.

## Database schema

Defined in `V1__criar_tabelas.sql`:

- `users`: name, unique email
- `room`: name, capacity (> 0), active flag
- `reservation`: user, room, status (`ACTIVE`, `CANCELLED`, `COMPLETED`), start/end time (end must be after start)
