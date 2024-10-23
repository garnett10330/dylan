package com.momo.dylantest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.annotation.Resource;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.alibaba.fastjson2.JSON;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.util.List;

@Configuration
//@ConfigurationProperties(prefix = "spring.redis.cluster")
@Getter
@Setter
public class RedisConfig {

    //List<String> nodes;
    @Value("${spring.redis.cluster.nodes}")
    private List<String> clusterNodes;

//    @Bean
//    public LettuceConnectionFactory redisConnectionFactory() {
//        System.out.println(JSON.toJSONString(clusterNodes));
//        System.out.println(JSON.toJSONString(nodes));
//        RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(clusterNodes);
//        // 如果有密碼，請取消下面這行的註釋
//        // clusterConfiguration.setPassword(redisProperties.getPassword());
//        return new LettuceConnectionFactory(clusterConfiguration);
//    }
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(new RedisClusterConfiguration(clusterNodes));
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory connectionFactory) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
        template.afterPropertiesSet();
        return template;
    }
}