package com.njtech.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Redis配置类
 *
 * @author chenxin
 * @date 2021/9/17 20:20
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory){

        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        // 为String类型key设置序列器
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 为String类型value设置序列器
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        // 为hash类型的key设置序列器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // 为hash类型的value设置序列器
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;

    }
}
