FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY . .
RUN ./gradlew build
CMD ["./gradlew", "bootRun"]