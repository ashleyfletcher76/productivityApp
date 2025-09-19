# Europace Todo App
Two Spring Boot microservices that work together:
- **user-service**: register, login, token verification
- **todo-service**: manage todos for authenticated users

For note, there is a docs directory with two files, `ideas.txt` and `steps.txt`. This is an idea into my thought process throughout the challenge and also the steps I generally took between each commit.

## Security
- **Auth model:** Opaque UUID tokens issued by `user-service` on `/login`, verified by `todo-service` on each request via `POST /token`
- **Where tokens live:** In-memory `ConcurrentHashMap` inside `user-service`, simple and thread-safe for this task
- **Request auth:** Clients must send `Authorization: Bearer <token>` to `todo-service`. Missing/invalid → **401**
- **Data access:** `GET /todos` is scoped to the authenticated user (derived from token). No cross user access, so **403** cannot occur
- **Passwords:** Plain text no hashing. Username/password length validated via `@Valid` annotation
- **Service-to-service call:** `todo-service` uses a `RestClient` to call `user-service`

* Note password length must be 5 characters, with at least one uppercase, one lowercase, one digit and one special character. Usually we would make this longer but for the purpose of this task/challenge its been kept shorter.
- Special chars == `@$!%*?&`

## Project prerequisites
* The following tools are needed, either install through their websites or 
    alternatively through homebrew, homebrew can be downloaded from here:
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)" 
```
* Then install these:
```bash
brew install make docker curl
brew install --cask postman
```
* `Make` - Build automation tool. [Install guide](https://www.gnu.org/software/make/)
* `Docker` - Containerization platform. [Install guide](https://docs.docker.com/get-docker/)
* `Postman` - API testing tool. [Download](https://www.postman.com/downloads/)
* `Curl` - Command-line tool for HTTP requests. [Install guide](https://curl.se/download.html)

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

## Example API flow
**Register**
* Postman: POST http://localhost:8081/register
* Body:
```json
{"username": "David", "password": "pass1!A"}
```
* Curl:
```bash
curl -X POST http://localhost:8081/register \
  -H "Content-Type: application/json" \
  -d '{"username":"David","password":"pass1!A"}' 
```

**Login**
* Postman: POST http://localhost:8081/login
* Body:
```json
{"username": "David", "password": "pass1!A"}
```
* Curl:
```bash
curl -X POST http://localhost:8081/login \
  -H "Content-Type: application/json" \
  -d '{"username":"David","password":"pass1!A"}'
```
* Response:
```json
{"token": "your-token-here"}
```

**Add todo**
* Postman: POST http://localhost:8082/todos
* Auth: Bearer Token (use token from login)
* Body:
```json
{"title": "Swim", "description": "Go swimming on tuesday", "finishBy": "2024-06-15"}
```
* Curl:
```bash
curl -X POST http://localhost:8082/todos \
  -H "Authorization: Bearer your-token-here" \
  -H "Content-Type: application/json" \
  -d '{"title":"Swim","description":"Go swimming","finishBy":"2024-06-15"}' 
```

**Get todos**
* Postman: GET http://localhost:8082/todos
* Auth: Bearer Token (use token from login)
* Curl:
```bash
curl -X GET http://localhost:8082/todos \
  -H "Authorization: Bearer your-token-here"
```

## Notes on responses
* 401 Unauthorized -> Missing or invalid token (handled by requireAuth)
* 403 Forbidden -> Would apply if a user accessed another user’s resource. Since this app only exposes “my todos” endpoints, 403 does not occur in practice
* 200 OK -> Successful request
* 201 Created -> Post request successfully
