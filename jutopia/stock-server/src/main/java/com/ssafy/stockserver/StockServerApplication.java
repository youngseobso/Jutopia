package com.ssafy.stockserver;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients // Feign client
public class StockServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockServerApplication.class, args);
    }
    @Bean
    // Feign 관련 로그출력하기 위한 bean 등록
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // 모든 레벨 등록
    }

}
