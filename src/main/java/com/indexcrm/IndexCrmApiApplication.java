package com.indexcrm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling; // Importante

@SpringBootApplication
@EnableScheduling // <--- ADICIONE ISSO
public class IndexCrmApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(IndexCrmApiApplication.class, args);
    }
}