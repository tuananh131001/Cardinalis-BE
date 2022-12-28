package com.cardinalis.timelineservice.config;

import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

// Configuration class to set up the redis configuration.
@Configuration
public class RedisConfig {
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory lcf = new LettuceConnectionFactory();
        lcf.afterPropertiesSet();
        return lcf;
    }

    // Setting up the redis template object.
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String ,Object>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        final RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
////        redisTemplate.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));
//        return redisTemplate;
//    }
}
