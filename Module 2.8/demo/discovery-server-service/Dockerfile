FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/discovery-server-service-*.jar discovery-server-service/app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "discovery-server-service/app.jar"]