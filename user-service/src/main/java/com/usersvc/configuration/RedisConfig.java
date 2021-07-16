package com.usersvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.usersvc.models.Role;

@Configuration
public class RedisConfig {

	
	@Bean
	JedisConnectionFactory jedisConnectionFactory()
	{
		JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
		jedisConFactory.setHostName("redis");
		jedisConFactory.setPort(6379);
		return jedisConFactory;
	
	}
	
	@Bean
	RedisTemplate<String, Role> redisTemplate() {
		RedisTemplate<String, Role> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory());
		return redisTemplate;
 	}
}
