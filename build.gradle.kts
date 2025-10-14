plugins {
    id("com.diffplug.spotless") version "7.0.0" apply false
}

subprojects {
    apply(plugin = "com.diffplug.spotless")

    // type-safe access when plugin applied via `apply(...)`
    extensions.configure<com.diffplug.gradle.spotless.SpotlessExtension> {
        java {
            target("src/**/*.java")
            googleJavaFormat("1.22.0")
            trimTrailingWhitespace()
            endWithNewline()
            targetExclude("**/build/**", "**/generated/**")
        }
    }
}

// optional aggregator
tasks.register("buildAll") {
    dependsOn(":backend:user-service:build",
        ":backend:todo-service:build",
        ":backend:auth-service:build")
}
