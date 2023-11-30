package com.ssafy.classserver;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ClassServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClassServerApplication.class, args);
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
