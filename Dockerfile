FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon
CMD ["java", "-jar", "build/libs/backend-rezeptapp-1.0.0.jar"]