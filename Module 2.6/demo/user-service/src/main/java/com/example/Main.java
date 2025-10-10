package com.example;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// Для запуска требуется рабочий контейнер с kafka, 
// обновить spring.kafka.bootstrap-servers соответственно
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);

        //Просто ставим в цикл до следующего ввода чего-то в консоль
        @SuppressWarnings("unused")
        Scanner scanner = new Scanner(System.in);
        @SuppressWarnings("unused")
        String inputLine = scanner.nextLine();
    }
}