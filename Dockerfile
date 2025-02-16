# Use a multi-stage build to reduce the final image size

# Stage 1: Build the Spring Boot application
FROM gradle:7.6-jdk17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files
COPY gradle /app/gradle
COPY build.gradle /app/
COPY settings.gradle /app/

# Copy the Kotlin source code
COPY src /app/src

# Perform the Gradle build
RUN gradle build -x test

# Stage 2: Create the final image
FROM openjdk:17-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/build/libs/*.jar /app/app.jar

# Expose the port the application runs on (typically 8080)
EXPOSE 8080

# Specify the command to run when the container starts
CMD ["java", "-jar", "app.jar"]