package com.ssafy.teacher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@Import(com.ssafy.common.objectmapper.ObjectMapperConfig.class)
public class TeacherApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeacherApplication.class, args);
    }

}
