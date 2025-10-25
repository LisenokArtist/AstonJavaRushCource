FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/cloud-config-service-*.jar cloud-config-service/app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "cloud-config-service/app.jar"]