# Etapa 1: build del proyecto
FROM maven:3.9.4-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY .mvn .mvn
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: imagen liviana para ejecutar
FROM eclipse-temurin:21-jdk

WORKDIR /app

COPY --from=build /app/target/patientapp-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "sleep 20 && java -jar app.jar"]