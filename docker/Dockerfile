FROM maven:3.8.3-openjdk-17-slim AS build
FROM openjdk:17

# Expose the port used by the application
EXPOSE 8090

# Set the working directory in the container
WORKDIR /llm
COPY llm-0.0.1-SNAPSHOT.jar /Configurations/application.properties ./

# Set the command to run when the container starts
CMD ["java","-jar","./llm-0.0.1-SNAPSHOT.jar", "--spring.config.location=./application.properties"]