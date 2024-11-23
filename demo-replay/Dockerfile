# Use OpenJDK 21 as the base image
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/temporalexperiments-1.0.0.jar /app/temporalexperiments-1.0.0.jar

# Expose any ports your application uses (optional, e.g., 8080)
EXPOSE 8080

# Set the default command to run the JAR file
ENTRYPOINT ["java", "-jar", "temporalexperiments-1.0.0.jar"]