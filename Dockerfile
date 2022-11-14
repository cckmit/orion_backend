from maven:3.6-jdk-16-alpine
RUN mvn clean install
COPY ./target/orionBackendJar.jar /app/orionBackendJar.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "orionBackendJar.jar"]
EXPOSE 8080