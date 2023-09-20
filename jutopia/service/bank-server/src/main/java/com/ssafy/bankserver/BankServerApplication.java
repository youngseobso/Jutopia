package com.ssafy.bankserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BankServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankServerApplication.class, args);
	}

}
