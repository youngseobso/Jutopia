package com.ssafy.classserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ClassServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(ClassServerApplication.class, args);
	}

}
