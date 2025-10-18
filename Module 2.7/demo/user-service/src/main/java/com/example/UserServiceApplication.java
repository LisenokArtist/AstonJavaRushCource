package com.example;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
// Для запуска требуется рабочий контейнер с kafka, 
// обновить spring.kafka.bootstrap-servers соответственно
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);

        //Просто ставим в цикл до следующего ввода чего-то в консоль
        @SuppressWarnings("unused")
        Scanner scanner = new Scanner(System.in);
        @SuppressWarnings("unused")
        String inputLine = scanner.nextLine();
    }
}