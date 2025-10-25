package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
// Для запуска требуется рабочий контейнер с kafka, 
// обновить spring.kafka.bootstrap-servers соответственно
public class UserApplicationService {
    public static void main(String[] args) {
        SpringApplication.run(UserApplicationService.class, args);
    }
}