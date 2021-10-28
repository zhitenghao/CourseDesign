/**
 * @Type com.swjt.community.config
 * @DESC
 * @Author Create By DaiRui
 * @TIME 2021/10/27  20:33
 * @VERSION Version 1.0
 **/

package com.swjt.community.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Bean
    RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();

        //设置连接池
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer=new Jackson2JsonRedisSerializer(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(new ObjectMapper());

        //Key 序列化
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //Value 序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
