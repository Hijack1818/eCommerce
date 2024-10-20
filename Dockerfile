# Use a base image with JDK
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven build output (the JAR file) into the container
COPY target/eCommerce-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (change if your application uses a different port)
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]
