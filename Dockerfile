# BUILD DO JAR
FROM maven:3.8.6-amazoncorretto-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src .src/
RUN mvn clean package -DskipTests


# CRIA A IMAGEM
FROM amazoncorretto:17
WORKDIR /app
COPY target/easyNutrition-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]