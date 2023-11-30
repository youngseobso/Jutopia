package com.ssafy.memberserver;

import feign.Logger;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.TimeZone;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class MemberServerApplication {
    @PostConstruct
    public void started(){
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
    public static void main(String[] args) {
        SpringApplication.run(MemberServerApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("/**");
                registry.addMapping("/**").allowedHeaders("/**");
//															"http://j9c108.p.ssafy.io:8000/",
//															"http://j9c108.p.ssafy.io:8000/**");
            }

        };
    }

    @Bean
    // Feign 관련 로그출력하기 위한 bean 등록
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; // 모든 레벨 등록
    }
}
