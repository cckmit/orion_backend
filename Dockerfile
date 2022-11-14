from openjdk:16-alpine
WORKDIR /Orion/Projects/orion_backend/
RUN mvn clean install
COPY ./target/orionBackendJar.jar /app/orionBackendJar.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "orionBackendJar.jar"]
EXPOSE 8080