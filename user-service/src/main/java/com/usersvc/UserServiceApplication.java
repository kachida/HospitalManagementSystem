package com.usersvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


// TODO: Auto-generated Javadoc
/**
 *  UserServiceApplication.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@SpringBootApplication
@EnableCaching
public class UserServiceApplication {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {SpringApplication.run(UserServiceApplication.class, args);
	}

}
