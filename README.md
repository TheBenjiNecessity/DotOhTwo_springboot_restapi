# DotOhTwo REST API

Spring Boot REST API for DotOhTwo.

## Requirements

- Java 21
- Maven
- Docker

## Running Locally

### Option 1: Fully local (self-contained)

Starts the app alongside its own Postgres and Kafka containers.

```bash
./mvnw package -DskipTests
docker compose up --build
```

### Option 2: External infrastructure

Connects to a separately running infra project on the `dotohtwolocalinfra` Docker network (Postgres, Kafka, Redis, Cassandra).

```bash
./mvnw package -DskipTests
docker compose -f docker-compose.external.yml up --build
```

## Spring Profiles

| Profile | Description |
|---------|-------------|
| `local` | Local Postgres on `localhost` |
| `docker` | Docker network hostnames (`postgres`, `kafka`, etc.) |
| `nosec` | Disables JWT authentication (for local development) |
| `dev` | AWS RDS Postgres (development environment) |
| `prod` | Production configuration |

## API Endpoints

### Users `/users`

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/users?username=` | Get user by username |
| `GET` | `/users/me` | Get the current authenticated user |
| `GET` | `/users/{id}` | Get user by ID |
| `GET` | `/users/search?q=&limit=` | Search users by username (default limit: 10) |
| `GET` | `/users/check?username=` | Check if a username is available (204 = available, 409 = taken) |
| `GET` | `/users/{id}/followers` | Get list of follower usernames for a user |
| `POST` | `/users` | Create a new user |
| `POST` | `/users/{id}/follow` | Follow a user (requires JWT) |
| `PUT` | `/users` | Update a user |
| `PUT` | `/users/complete-profile` | Complete the current user's profile (requires JWT) |
| `DELETE` | `/users/{id}` | Delete a user |

### Reviewables `/reviewables`

| Method | Path | Description |
|--------|------|-------------|
| `GET` | `/reviewables/{id}` | Get reviewable by ID |
| `GET` | `/reviewables/search?q=&lang=&limit=` | Search reviewables by title (default lang: `en`, default limit: 10) |
| `POST` | `/reviewables` | Create a new reviewable |
| `PUT` | `/reviewables/{id}` | Update a reviewable |
| `DELETE` | `/reviewables/{id}` | Delete a reviewable |

## Kafka Topics

| Topic | Published when |
|-------|----------------|
| `users.created` | A new user is created |
| `reviewables.created` | A new reviewable is created |

## Database Migrations

Flyway migrations are applied automatically on startup from `src/main/resources/db/migration`.
