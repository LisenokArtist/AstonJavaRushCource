package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerService {
    /*
     * Для запуска требуется рабочий контейнер с kafka
     *  - Запустить DiscoveryServerService
     *  - Запустить UserNotificationApplicationService
     *  - Запустить UserApplicationService
     *  По адресу http://localhost:8761 отобразятся имена 2-х последних сервисов
     */
    public static void main(String[] args) {
        SpringApplication.run(DiscoveryServerService.class, args);
    }
}