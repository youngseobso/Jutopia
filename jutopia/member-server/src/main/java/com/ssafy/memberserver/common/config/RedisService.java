package com.ssafy.memberserver.common.config;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisTemplate redisTemplate;

    public String getValues(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }
    public void setValues(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }
    public void setValuesWithTimeout(String key, String value, long timeout){
        redisTemplate.opsForValue().set(key,value);
        redisTemplate.expire(key,timeout, TimeUnit.SECONDS);
    }

    public void deleteValues(String key){
        redisTemplate.delete(key);
    }
}