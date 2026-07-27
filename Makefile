.PHONY: build up up-external up-external-jwt down down-external down-external-jwt logs

# Compile the app without running tests
build:
	./mvnw package -DskipTests

# Option 1: fully local (self-contained), see README
up: build
	docker compose up --build

down:
	docker compose down

# Option 2: external infra (dotohtwolocalinfra network), see README
up-external: build
	docker compose -f docker-compose.external.yml up --build

down-external:
	docker compose -f docker-compose.external.yml down

# Option 2 + local JWT mode
up-external-jwt: build
	docker compose -f docker-compose.external.yml -f docker-compose.local-jwt.yml up --build

down-external-jwt:
	docker compose -f docker-compose.external.yml -f docker-compose.local-jwt.yml down

logs:
	docker compose logs -f
