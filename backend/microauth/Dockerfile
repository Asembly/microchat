FROM maven:3.8.4-openjdk-17-slim AS builder
WORKDIR /app
COPY libs/*.jar /tmp/
RUN for jar_file in /tmp/*.jar; do \
      filename=$(basename "$jar_file") && \
      artifact_name="${filename%-*}" && \
      version="${filename##*-}" && \
      version="${version%.jar}" && \
      mvn install:install-file \
        -Dfile="$jar_file" \
        -DgroupId=asembly \
        -DartifactId="$artifact_name" \
        -Dversion="$version" \
        -Dpackaging=jar \
        -DgeneratePom=true; \
    done
COPY . .
RUN chmod +x mvnw
RUN mvn clean install -DskipTests
RUN ./mvnw -B package


FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]