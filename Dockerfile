FROM 3.8.6-eclipse-temurin-11-alpine
WORKDIR /build
COPY . /build
RUN mvn clean install
COPY /build/target/orionBackendJar.jar /app/orionBackendJar.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "orionBackendJar.jar"]
EXPOSE 8080