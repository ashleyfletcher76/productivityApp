FROM gradle:8.7-jdk21 AS build

WORKDIR /workspace

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew bootJar --no-daemon -x test

# now run

FROM eclipse-temurin:21-jre

COPY --from=build /workspace/build/libs/*.jar /app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]