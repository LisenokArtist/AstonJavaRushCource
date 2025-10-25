FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/gateway-service-*.jar gateway-service/app.jar

EXPOSE 8888 8082 8083

ENTRYPOINT ["java", "-jar", "gateway-service/app.jar"]