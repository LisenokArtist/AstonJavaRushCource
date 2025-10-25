FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/user-service-*.jar user-service/app.jar

EXPOSE 8080 5432 9092 9093

ENTRYPOINT ["java", "-jar", "user-service/app.jar"]