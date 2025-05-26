# Build-Stage mit Gradle & JDK
FROM gradle:jdk21-jammy AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# ⛔ Tests werden hier bewusst übersprungen
RUN gradle build -x test --no-daemon

# Runtime-Stage mit JDK
FROM eclipse-temurin:21-jdk-jammy
COPY --from=build /home/gradle/src/build/libs/backend-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]