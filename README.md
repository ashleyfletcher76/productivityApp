# Productivity One

## Microservices
- **user-service** — registration & user data
- **todo-service** — CRUD for user-specific todos
- **auth-service** — issues/verifies **asymmetric JWT (RSA)**
> Each service is an **independent Spring Boot app** with its **own Docker container** and dependencies.

## Docs
- `docs/ideas.txt`, `docs/steps.txt` — notes from the original challenge
- `docs/API_FLOW.md` — example API flow (register/login/token/todos)

## Security
- **Model:** JWT (RS256) from `auth-service`
- **Client auth:** send `Authorization: Bearer <jwt>` to backend services
- **Access scope:** `todo-service` only returns **my todos**
- **Service-to-service:** `todo-service` validates JWT (RSA public key / resource-server config)

## Project prerequisites
* The following tools are needed, either install through their websites or alternatively through homebrew, homebrew can be downloaded from here:
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```
* Then install these:
```bash
brew install make docker curl
brew install --cask postman
```
* Make - Build automation tool. [Install guide](https://www.gnu.org/software/make/)
* Docker - Containerization platform. [Install guide](https://docs.docker.com/get-docker/)
* Postman - API testing tool. [Download](https://www.postman.com/downloads/)
* Curl - Command-line tool for HTTP requests. [Install guide](https://curl.se/download.html)

## Environment variables
* For simplicity in this challenge, variables are stored in .env inside the docker directory:
```bash
SPRING_DATASOURCE_URL_USER=jdbc:h2:mem:testdb
SPRING_DATASOURCE_USERNAME_USER=useruser
SPRING_DATASOURCE_PASSWORD_USER=pwuser
SERVER_PORT_USER=8081

SPRING_DATASOURCE_URL_TODO=jdbc:h2:mem:testdb
SPRING_DATASOURCE_USERNAME_TODO=usertodo
SPRING_DATASOURCE_PASSWORD_TODO=pwtodo
SERVER_PORT_TODO=8082
USER_SERVICE_BASE_URL=http://user-service:8081
```
## Run with Make
* Build and begin project from root directory level
```bash
make build-up   # build and start
make down       # stop containers
make logs       # follow logs
make clean      # remove docker files
```
## Run with Docker Compose
* Build and begin project from root directory level
```bash
docker-compose -f docker/docker-compose.yaml -p europace build
docker-compose -f docker/docker-compose.yaml -p europace up -d
docker-compose -f docker/docker-compose.yaml -p europace down
docker-compose -f docker/docker-compose.yaml -p europace logs -f
docker-compose -f docker/docker-compose.yaml -p europace down -v --remove-orphans
```

## Example API Flow
See [API Flow](docs/API_FLOW.md)

## Notes
* This is a learning project, not production-grade.
* Each service runs as a standalone container.
* See [Productivity One – Project Overview](docs/PROJECT_OVERVIEW.md)