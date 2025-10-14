pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // optional
    repositories {
        mavenCentral()
    }
}

rootProject.name = "productivity-one"
include("backend:user-service", "backend:todo-service", "backend:auth-service")
