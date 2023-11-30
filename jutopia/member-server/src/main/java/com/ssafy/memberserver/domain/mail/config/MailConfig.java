package com.ssafy.memberserver.domain.mail.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class MailConfig {
    private final Environment environment;
    private static final String id = "hans0537@naver.com";

    private static final String password = "hans1224";

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        log.info("env:{}",environment.getProperty(id));
        log.info("id:{}",id);

        javaMailSender.setHost("smtp.naver.com");
        javaMailSender.setUsername(id);
        javaMailSender.setPassword(password);

        javaMailSender.setPort(465);

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.starttls.enable", "true");
        properties.setProperty("mail.debug", "true");
        properties.setProperty("mail.smtp.ssl.trust","smtp.naver.com");
        properties.setProperty("mail.smtp.ssl.enable","true");
        return properties;
    }
}
