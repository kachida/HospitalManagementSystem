package com.vitalsignsvc.feign;

import org.springframework.context.annotation.Bean;

import com.vitalsignsvc.models.YamlProperties;

import feign.RequestTemplate;
import feign.RequestInterceptor;


public class PatientServiceClientConfiguration {
	
	private final YamlProperties yamlProperties;
	public PatientServiceClientConfiguration(YamlProperties yamlProperties) {
		super();
		this.yamlProperties = yamlProperties;
	}

	@Bean
	public RequestInterceptor bearerTokenRequestInterceptor() {
		return new RequestInterceptor() {
			@Override
			public void apply(RequestTemplate template)
			{
				String patientServiceAccessToken = yamlProperties.getPatientServiceAccessToken();
				template.header("Authorization", String.format("Bearer %s", patientServiceAccessToken));
			}
		};
	}

}
