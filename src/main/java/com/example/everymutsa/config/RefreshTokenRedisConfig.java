package com.example.everymutsa.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RefreshTokenRedisConfig extends RedisConfig {
    @Bean
    public RedisConnectionFactory refreshTokenRedisConnectionFactory() {
        return createLettuceConnectionFactory(0);
    }
    @Bean
    @Qualifier("refreshTokenRedisTemplate")
    RedisTemplate<String, String> refreshTokenRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(refreshTokenRedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}