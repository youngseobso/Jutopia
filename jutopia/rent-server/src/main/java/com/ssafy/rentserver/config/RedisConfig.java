package com.ssafy.rentserver.config;

import com.ssafy.common.objectmapper.ObjectMapperConfig;
import com.ssafy.rentserver.dto.SeatResponse;
import com.ssafy.rentserver.model.Seat;
import io.lettuce.core.RedisURI;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.List;

@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisConfig {

    private final RedisProperties redisProperties;
    private final ObjectMapperConfig objectMapperConfig;

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisURI redisURI = RedisURI.create(redisProperties.getUrl());
        RedisConfiguration configuration = LettuceConnectionFactory.createRedisConfiguration(redisURI);
        LettuceConnectionFactory factory = new LettuceConnectionFactory(configuration);
        factory.afterPropertiesSet();
        return factory;
    }


    @Bean
    public RedisTemplate<String, Seat> seatRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Seat> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapperConfig.objectMapper(),Seat.class));

        return redisTemplate;
    }

    @Bean
    public RedisTemplate<String, List<SeatResponse>> seatsRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, List<SeatResponse>> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(objectMapperConfig.objectMapper(),List.class));

        return redisTemplate;
    }

}
