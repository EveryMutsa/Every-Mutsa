package com.example.everymutsa.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class ViewCountRedisConfig extends RedisConfig{

    @Bean
    public RedisConnectionFactory viewCountConnectionFactory() {
        return createLettuceConnectionFactory(1); // Redis DB 선택
    }

    @Bean
    @Qualifier("viewCountRedisTemplate")
    public RedisTemplate<String, String> viewCountRedisTemplate() {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setConnectionFactory(viewCountConnectionFactory());
        return redisTemplate;
    }


    @Bean
    public RedisCacheManager redisViewCountCacheManager() {
        RedisCacheConfiguration redisCacheConfig = CacheConfiguration();

        return RedisCacheManager
                .RedisCacheManagerBuilder
                .fromConnectionFactory(viewCountConnectionFactory())
                .cacheDefaults(redisCacheConfig)
                .transactionAware()
                .build();
    }

    private RedisCacheConfiguration CacheConfiguration() {
        // key    문자열을 바이트 배열로 변환하여 저장
        RedisSerializationContext.SerializationPair<String> keySerializationPair = RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer());
        // value  값은 객체 형태로 저장 : Java 객체를 JSON 형태로 직렬화하는 설정
        RedisSerializationContext.SerializationPair<Object> valueSerializationPair = RedisSerializationContext.SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer());

        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(keySerializationPair)
                .serializeValuesWith(valueSerializationPair);
    }
}
