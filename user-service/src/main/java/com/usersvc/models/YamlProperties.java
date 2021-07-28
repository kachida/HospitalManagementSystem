package com.usersvc.models;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


// TODO: Auto-generated Javadoc
/**
 * The Class YamlProperties.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Component
@ConfigurationProperties(prefix="api")

/**
 * Instantiates a new yaml properties.
 */
@Data
public class YamlProperties {
	
	/** The username. */
	private String username;
	
	/** The password. */
	private String password;
	
	/** The secret key. */
	private String secret_key;
	
	

}
