# Stage 1: Build the application with Java 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DskipTests

# Stage 2: Create the final image with Java 17 JRE
FROM eclipse-temurin:17-jre
COPY --from=build /app/target/calculator-app-1.0-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]