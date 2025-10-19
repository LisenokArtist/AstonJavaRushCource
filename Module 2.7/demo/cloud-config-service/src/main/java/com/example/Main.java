package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
// Для запуска требуется рабочий контейнер с kafka, 
// обновить spring.kafka.bootstrap-servers соответственно
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}