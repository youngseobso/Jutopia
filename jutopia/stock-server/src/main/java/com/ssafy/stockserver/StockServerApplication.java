package com.ssafy.stockserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StockServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockServerApplication.class, args);
    }

}
