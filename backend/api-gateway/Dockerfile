FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app
COPY . .
RUN chmod +x mvnw
RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]