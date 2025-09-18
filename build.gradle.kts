tasks.register("buildAll") {
    dependsOn(":backend:user-service:build", ":backend:todo-service:build")
}