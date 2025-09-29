# Productivity One – Project Overview
## What This Is
* Learning project → evolving from Europace coding challenge.
* Goal: Full-scale microserviced productivity app (similar to Notion).
* For now: backend-focused, later: proper frontend + cloud deployment.
## Current State
* Repo setup:
    * Root settings.gradle.kts includes user-service, todo-service, auth-service.
    * Root build.gradle.kts has buildAll task for all services.
* Docker:
    * docker-compose.yaml orchestrates services.
    * Each service = own container, own dependencies, own DB volume.
    * Makefile for easy build/run.
* Microservices:
    * user-service: registration & user data.
    * todo-service: CRUD for todos (user-specific).
    * auth-service: issues/verifies asymmetric JWT tokens (RSA keys).
* Security:
    * Started with HttpServletRequest requireAuth.
    * Now uses Spring SecurityFilterChain.
    * auth-service fully manages token lifecycle.
## Planned Next Steps
* Add proper frontend (framework TBD).
* Deploy to cloud → later add Kubernetes.
* Expand beyond todos (notes, docs, projects).
* Improve CI/CD pipelines.