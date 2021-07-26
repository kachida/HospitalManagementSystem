package com.elasticsearchsvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ElasticSearchServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ElasticSearchServiceApplication.class, args);
	}
	
}
