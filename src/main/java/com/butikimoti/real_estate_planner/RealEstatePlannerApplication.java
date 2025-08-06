package com.butikimoti.real_estate_planner;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RealEstatePlannerApplication {

    public static void main(String[] args) {
        // load .env file
        Dotenv dotenv = Dotenv.configure()
                .filename("variables.env")
                .ignoreIfMissing()
                .load();

        dotenv.entries().forEach(entry ->
                System.setProperty(entry.getKey(), entry.getValue())
        );

        // run
        SpringApplication.run(RealEstatePlannerApplication.class, args);
    }
}
