# Etapa 1: construir el proyecto con Maven y Java 17
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa 2: usar solo el .jar generado
FROM eclipse-temurin:17
WORKDIR /app
COPY --from=build /app/target/futbol-1.jar app.jar
EXPOSE 8100
ENTRYPOINT ["java", "-jar", "app.jar"]
