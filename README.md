# DotOhTwo REST API

Spring Boot REST API for DotOhTwo.

## Requirements

- Java 21
- Maven
- Docker

## Running Locally

A `Makefile` is included to simplify the commands below. Run `make` targets from the project root:

| Target                 | Description                                             |
| ----------------------- | -------------------------------------------------------- |
| `make up`               | Option 1: fully local (self-contained)                   |
| `make up-external`      | Option 2: external infrastructure                        |
| `make up-external-jwt`  | Option 2 with local JWT mode enabled                      |
| `make down` / `down-external` / `down-external-jwt` | Tear down the corresponding stack       |
| `make logs`             | Tail logs for the currently running stack                |

### Option 1: Fully local (self-contained)

Starts the app alongside its own Postgres and Kafka containers.

```bash
make up
# equivalent to:
./mvnw package -DskipTests
docker compose up --build
```

### Option 2: External infrastructure

Connects to a separately running infra project on the `dotohtwolocalinfra` Docker network (Postgres, Kafka, Redis, Cassandra).

```bash
make up-external
# equivalent to:
./mvnw package -DskipTests
docker compose -f docker-compose.external.yml up --build
```

Use `make up-external-jwt` to also enable local JWT mode (see [Local JWT Testing](#local-jwt-testing) below).

## Spring Profiles

| Profile  | Description                                          |
| -------- | ---------------------------------------------------- |
| `local`  | Local Postgres on `localhost`                        |
| `docker` | Docker network hostnames (`postgres`, `kafka`, etc.) |
| `nosec`  | Disables JWT authentication entirely                 |
| `dev`    | AWS RDS Postgres (development environment)           |
| `prod`   | Production configuration                             |

## Local JWT Testing

By default, the API validates JWTs against AWS Cognito. The `local` profile switches to a symmetric HS256 secret so you can generate your own tokens without an auth service — useful for a local testing frontend.

```bash
make up-external-jwt
# equivalent to:
./mvnw package -DskipTests
docker compose -f docker-compose.external.yml -f docker-compose.local-jwt.yml up --build
```

To generate a token (e.g. in a Next.js app), use the [`jose`](https://github.com/panva/jose) package with the same secret:

```typescript
import { SignJWT } from "jose";

const SECRET = new TextEncoder().encode(
    "local-dev-secret-key-minimum-32-bytes!!",
);

const token = await new SignJWT({ sub: "testuser" })
    .setProtectedHeader({ alg: "HS256" })
    .setIssuedAt()
    .setExpirationTime("8h")
    .sign(SECRET);
```

The secret is set via the `JWT_LOCAL_SECRET` env var in `docker-compose.local-jwt.yml`. Change it there and in your frontend if you want a different value.

## API Endpoints

### Users `/users`

| Method   | Path                      | Description                                                     |
| -------- | ------------------------- | --------------------------------------------------------------- |
| `GET`    | `/users?username=`        | Get user by username                                            |
| `GET`    | `/users/me`               | Get the current authenticated user                              |
| `GET`    | `/users/{id}`             | Get user by ID                                                  |
| `GET`    | `/users/search?q=&limit=` | Search users by username (default limit: 10)                    |
| `GET`    | `/users/check?username=`  | Check if a username is available (204 = available, 409 = taken) |
| `GET`    | `/users/{id}/followers`   | Get list of follower usernames for a user                       |
| `POST`   | `/users`                  | Create a new user                                               |
| `POST`   | `/users/{id}/follow`      | Follow a user (requires JWT)                                    |
| `PUT`    | `/users`                  | Update a user                                                   |
| `PUT`    | `/users/complete-profile` | Complete the current user's profile (requires JWT)              |
| `DELETE` | `/users/{id}`             | Delete a user                                                   |

### Reviewables `/reviewables`

| Method   | Path                                  | Description                                                         |
| -------- | ------------------------------------- | ------------------------------------------------------------------- |
| `GET`    | `/reviewables/{id}`                   | Get reviewable by ID                                                |
| `GET`    | `/reviewables/search?q=&lang=&limit=` | Search reviewables by title (default lang: `en`, default limit: 10) |
| `POST`   | `/reviewables`                        | Create a new reviewable                                             |
| `PUT`    | `/reviewables/{id}`                   | Update a reviewable                                                 |
| `DELETE` | `/reviewables/{id}`                   | Delete a reviewable                                                 |

## Kafka Topics

| Topic                 | Published when              |
| --------------------- | --------------------------- |
| `users.created`       | A new user is created       |
| `reviewables.created` | A new reviewable is created |

## Database Migrations

Flyway migrations are applied automatically on startup from `src/main/resources/db/migration`.
