package com.vitalsignsvc.feign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.vitalsignsvc.models.YamlProperties;

import feign.RequestTemplate;
import feign.RequestInterceptor;


public class PatientServiceClientConfiguration {
	
	@Autowired
	YamlProperties yamlProperties;

	
	
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