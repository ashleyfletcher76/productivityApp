PROJECT ?= productivity-app

DOCKER_COMPOSE = docker-compose -f docker/docker-compose.yaml -p $(PROJECT)

up:
	$(DOCKER_COMPOSE) up -d

down:
	$(DOCKER_COMPOSE) down

build-up:
	$(DOCKER_COMPOSE) build
	$(DOCKER_COMPOSE) up -d

clean:
	$(DOCKER_COMPOSE) down -v --remove-orphans

re: down up

re-new: clean build-up

logs:
	$(DOCKER_COMPOSE) logs -f

.PHONY: up down build-up re re-new logs