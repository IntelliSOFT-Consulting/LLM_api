#FROM maven:3.6.3-jdk-11-slim AS build
#FROM openjdk:11

FROM maven:3.8.3-openjdk-17-slim AS build
FROM eclipse-temurin:17-jdk-alpine
EXPOSE 8090
WORKDIR /llm_javabackend
COPY . /llm_javabackend
#HEALTHCHECK --interval=25s --timeout=3s --retries=2 CMD wget --spider http://199.192.27.107:8085/actuator/health || exit 1
ADD target/llm-0.0.1-SNAPSHOT.jar llm-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","target/llm-0.0.1-SNAPSHOT.jar"]