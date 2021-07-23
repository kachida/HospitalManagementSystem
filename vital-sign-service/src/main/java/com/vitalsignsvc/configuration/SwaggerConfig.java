package com.vitalsignsvc.configuration;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.builders.ApiInfoBuilder;



@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfig extends WebMvcConfigurationSupport {
	
	

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String DEFAULT_INCLUDE_PATTERN = "/vitalsignsvc.*";

    
	@Bean
	public Docket vitalSignApi()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.securityContexts(Arrays.asList(securityContext()))
				.securitySchemes(Arrays.asList(apiKey()))
				.groupName("vitalsign")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.vitalsignsvc.controller"))
				.paths(regex(DEFAULT_INCLUDE_PATTERN))
				.build()
				.apiInfo(vitalSignApiMetaData());
				
	}
	

	
	private ApiInfo vitalSignApiMetaData() {
		
		return new ApiInfoBuilder()
				.title("Vital Sign API")
				.description("Spring Boot User API for performing CRUD operations on vital sign module")
				.version("1.0.0")
				.license("Apache License Version 2.0")
				.licenseUrl("https://www.apache.org/licenses/LICENSE-2.0\"")
				.contact(new Contact("Kannappan","https://github.com/kachida","kannappan.chidambaram@gmail.com"))
				.build();
		
	}
	
	
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations("classpath:/META-INF/resources");
		
		registry.addResourceHandler("webjars/**")
				.addResourceLocations("classpath:/META-INF/resources/webjars/");
		
	}
	
	
	private ApiKey apiKey(){
		return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
	}
	
	private SecurityContext securityContext()
	{
		/*
		return SecurityContext
				.builder()
				.securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex(DEFAULT_INCLUDE_PATTERN))
				.build();
				*/
		return SecurityContext
				.builder()
				.securityReferences(defaultAuth())
				.operationSelector( oc -> oc.requestMappingPattern().matches(DEFAULT_INCLUDE_PATTERN))
				.build();
	}
	
	List<SecurityReference> defaultAuth()
	{
		AuthorizationScope authorizationScope = new AuthorizationScope("global","accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT",authorizationScopes));
	}
	

}
