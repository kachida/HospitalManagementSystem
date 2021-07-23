package com.patientsvc.feign;


import org.springframework.context.annotation.Bean;

import com.patientsvc.models.YamlProperties;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class UserServiceClientConfiguration {
	
	private final YamlProperties yamlProperties;

	public UserServiceClientConfiguration(YamlProperties yamlProperties) {
		super();
		this.yamlProperties = yamlProperties;
	}



	@Bean
	public RequestInterceptor bearerTokenRequestInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate template)
			{
				String userServiceAccessToken = yamlProperties.getUserServiceAccessToken();
				template.header("Authorization", String.format("Bearer %s", userServiceAccessToken));
			}
		};
	}

}
