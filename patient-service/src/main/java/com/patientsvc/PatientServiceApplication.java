package com.patientsvc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Kannappan
 *
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class PatientServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(PatientServiceApplication.class, args);
	}

}
