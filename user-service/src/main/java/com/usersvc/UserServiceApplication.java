package com.usersvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class UserServiceApplication {
	
	public static void main(String[] args) {SpringApplication.run(UserServiceApplication.class, args);
	}

}
