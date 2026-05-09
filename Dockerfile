# syntax=docker/dockerfile:1

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom first for dependency caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline

# Copy source code
COPY src ./src

# Build app
RUN mvn clean package -DskipTests


FROM eclipse-temurin:17-jre

WORKDIR /app

EXPOSE 8080

# Create app folders
RUN mkdir -p /app/songs /app/images

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]