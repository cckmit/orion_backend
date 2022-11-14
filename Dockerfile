FROM maven:3.6-jdk-8-alpine
WORKDIR /build
COPY . /build
RUN mvn clean install
COPY /build/target/orionBackendJar.jar /app/orionBackendJar.jar
WORKDIR /app
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "orionBackendJar.jar"]
EXPOSE 8080