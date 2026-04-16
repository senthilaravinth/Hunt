# Stage 1: Build the application
FROM maven:3.8.5-openjdk-11-slim AS build
COPY src /app/src
COPY pom.xml /app
RUN mvn -f /app/pom.xml clean package -DskipTests

# Stage 2: Create the final small image
FROM openjdk:11-jre-slim
COPY --from=build /app/target/calculator-app-1.0-SNAPSHOT.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]