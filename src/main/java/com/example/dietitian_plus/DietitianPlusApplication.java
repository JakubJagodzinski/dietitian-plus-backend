package com.example.dietitian_plus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class DietitianPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(DietitianPlusApplication.class, args);
    }

}
