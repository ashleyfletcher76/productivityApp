# Europace Todo App

# Project prerequisites
* The following tools are needed, either install through their websites or alternatively through homebrew, homebrew can be downloaded from here:
```bash
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)" 
```
* `Make` - Build automation tool. [Install guide](https://www.gnu.org/software/make/)
```bash
brew install make
```
* `Docker` - Containerization platform. [Install guide](https://docs.docker.com/get-docker/)
```bash
brew install docker
```
* `Postman` - API testing tool. [Download](https://www.postman.com/downloads/)
```bash
brew install --cask postman
```
* `Curl` - Command-line tool for HTTP requests. [Install guide](https://curl.se/download.html)
```bash
brew install curl 
```

**Environment variables**
* Due to this being a practice project, the environment variables are available in this README.md, in normal circumstances this would not be allowed.
* Please create an env file within the docker directory either via the terminal with:
```bash
touch .env
```
* or use your IDE.
* Paste the following values into the `.env` file:
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

# Important commands to build project with `Make`
* Build and begin project
```bash
make build-up
```
* Stop containers running
```bash
make down
```
* Check docker logs
```bash
make logs
```
* Remove docker files
```bash
make clean
```

# Important commands to build project through docker
* Build and begin project
```bash
docker-compose -f docker/docker-compose.yaml -p europace build
```
```bash
docker-compose -f docker/docker-compose.yaml -p europace up -d
```
* Stop containers running
```bash
docker-compose -f docker/docker-compose.yaml -p europace down
```
* Check docker logs
```bash
docker-compose -f docker/docker-compose.yaml -p europace logs -f
```
* Remove docker files
```bash
docker-compose -f docker/docker-compose.yaml -p europace down -v --remove-orphans
```

# Application process for Postman
**Register user**
* Create a new POST request and enter this URL and the following json as the body in raw selection(JSON)
```bash
http://localhost:8081/register
```
```json
{"username": "David", "password": "password"}
```

**Login user**
* Create a new POST request and enter this URL and the following json as the body in raw selection(JSON)
```bash
http://localhost:8081/login
```
```json
{"username": "David", "password": "password"}
```

**Todo process**
* Create a new POST request and enter this URL and the following json as the body in raw selection(JSON). Go to `Authorization` selection and choose Bearer Token and in the corresponding box, take the token from previous login request. 
```bash
http://localhost:8082/todos
```
```json
{"title": "Swim", "description": "Go swimming on tuesday", "finishBy": "2024-06-15"}
```

* Create a new GET request and enter this URL. Go to `Authorization` selection and choose Bearer Token and in the corresponding box, take the token from previous login request.
```bash
http://localhost:8082/todos
```

#SQL commands for H2 console
* Insert a user
```bash
INSERT INTO users (username, password) VALUES ('test', 'test');
```
* View all users in db
```bash
SELECT * FROM users;
```