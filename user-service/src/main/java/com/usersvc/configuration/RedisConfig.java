package com.usersvc.configuration;

import java.time.Duration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisClientConfigurationBuilder;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.usersvc.models.Role;

@Configuration
public class RedisConfig {

	
	@Bean
	JedisConnectionFactory jedisConnectionFactory()
	{
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("redis");
        redisStandaloneConfiguration.setPort(6379);;
        JedisClientConfigurationBuilder jedisClientConfiguration = JedisClientConfiguration.builder();
        jedisClientConfiguration.connectTimeout(Duration.ofSeconds(60));
        
        
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory(redisStandaloneConfiguration,jedisClientConfiguration.build());
		return jedisConFactory;
	
	}
	
	@Bean
	RedisTemplate<String, Role> redisTemplate() {
		RedisTemplate<String, Role> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
 	}
}
