package com.configserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableConfigServer
@EnableDiscoveryClient
public class ConfigServerApplication {

		public static void main(String[] args)
		{
			SpringApplication.run(ConfigServerApplication.class,args);
		}
		
		@RestController
		class MessageRestController {

		  @Value("${message:Hello default}")
		  private String message;

		  @RequestMapping("/message")
		  String getMessage() {
		    return this.message;
		  }
		}
}
