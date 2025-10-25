FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/user-notification-service-*.jar user-notification-service/app.jar

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "user-notification-service/app.jar"]