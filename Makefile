DOCKER_COMPOSE = docker-compose -f docker/docker-compose.yaml

up:
	$(DOCKER_COMPOSE) up -d

down:
	$(DOCKER_COMPOSE) down

build-up:
	$(DOCKER_COMPOSE) build up

re: down up

logs:
	$(DOCKER_COMPOSE) logs -f

.PHONY: up down build-up re logs