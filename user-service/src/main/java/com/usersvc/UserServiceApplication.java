package com.usersvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import clover.org.apache.log4j.Logger;


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
	
	private static final Logger elkLog = Logger.getLogger(UserServiceApplication.class);
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	
	public static void main(String[] args) {SpringApplication.run(UserServiceApplication.class, args);
		elkLog.info("UserServiceApplication - ELK Log");
	}

}
