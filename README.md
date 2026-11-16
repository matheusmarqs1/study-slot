# Study Slot

A study room reservation backend built with Spring Boot. Early stage: the database schema is defined and CRUD endpoints with DTOs and request validation are available for rooms, users, and reservations.

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

Request and response bodies are DTOs, and request bodies are validated with Bean Validation (invalid input returns `400`).

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
  "startsAt": "2026-11-20T10:00:00Z",
  "endsAt": "2026-11-20T11:00:00Z"
}
```

Response:

```json
{
  "id": 1,
  "user": { "id": 1, "name": "Maria Souza" },
  "room": { "id": 1, "name": "Study Room 01" },
  "startsAt": "2026-11-20T10:00:00Z",
  "endsAt": "2026-11-20T11:00:00Z",
  "status": "ACTIVE",
  "createdAt": "2026-11-16T15:00:00Z"
}
```

`PUT /api/v1/reservations/{id}` only reschedules: it accepts `startsAt` and `endsAt`. The status is not editable through it.

### Current limitations

- Validation failures return `400` with Spring's default error body, not a custom format yet.
- Missing ids, duplicate e-mails and deleting a room or user that has reservations return `500` (no global error handling yet).
- No business rules yet (overlapping reservations, cancellation).

## Database schema

Defined in `V1__criar_tabelas.sql`:

- `users`: name, unique email
- `room`: name, capacity (> 0), active flag
- `reservation`: user, room, status (`ACTIVE`, `CANCELLED`, `COMPLETED`), start/end time (end must be after start)
