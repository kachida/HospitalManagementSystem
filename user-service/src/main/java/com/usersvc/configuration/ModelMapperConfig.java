package com.usersvc.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// TODO: Auto-generated Javadoc
/**
 * ModelMapperConfig.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Configuration
public class ModelMapperConfig {

	 /**
 	 * Model mapper.
 	 *
 	 * @return the model mapper
 	 */
 	@Bean
	    public ModelMapper modelMapper() {
	        return new ModelMapper();
	    }
	
}
