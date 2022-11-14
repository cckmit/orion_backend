FROM maven:3.8.5-eclipse-temurin-11-focal
WORKDIR /build
COPY . /build
RUN mvn clean install
COPY /target/orionBackendJar.jar /app/orionBackendJar.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "orionBackendJar.jar"]
EXPOSE 8080