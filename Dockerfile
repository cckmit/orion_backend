FROM maven:3.8.5-eclipse-temurin-11-focal
WORKDIR /build
COPY . /build
COPY /target/orionBackendJar.jar /app/orionBackendJar.jar
COPY /src/main/java/br/com/live/reports /app/
COPY ./fonts ./
RUN mkdir -p /usr/share/fonts/truetype/
RUN install -m644 arial-black.ttf /usr/share/fonts/truetype/
RUN install -m644 arial.ttf /usr/share/fonts/truetype/
ENV TZ=America/Sao_Paulo
# Instala o pacote tzdata para definir o fuso horário
RUN apk add --no-cache tzdata
# Configura o fuso horário no sistema
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
WORKDIR /app
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=homol", "orionBackendJar.jar"]
EXPOSE 8080