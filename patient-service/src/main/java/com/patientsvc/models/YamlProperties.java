package com.patientsvc.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix="api")
@Data
public class YamlProperties {
	
	private String username;
	private String password;
	private String secret_key;
	
	

}
