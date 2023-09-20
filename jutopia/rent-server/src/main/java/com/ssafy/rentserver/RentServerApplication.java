package com.ssafy.rentserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RentServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RentServerApplication.class, args);
    }

}
