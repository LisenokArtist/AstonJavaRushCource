package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayService {
    /*
     * Выполняет перенаправление запросов на 2 сервиса:
     *  - user-service (8082)
     *  - user-notification-service (8083)
     * Настройки в Routes.java
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayService.class, args);
    }
}