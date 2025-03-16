package com.azsyed.lcbeerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LcBeerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LcBeerServiceApplication.class, args);
    }
}
