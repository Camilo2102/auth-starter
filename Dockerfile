# Use a base image with Java to run the JAR
FROM openjdk:20

# Set the working directory in the container
WORKDIR /app

# Copy the pre-built JAR file from your local machine into the container
COPY ./target/auth-starter.jar /app/auth-starter.jar

# Expose port 8080 for the application
EXPOSE 8080

# Define the entry point to run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "auth-starter.jar"]
