package com.patientsvc.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.patientsvc.filter.JwtRequestFilter;
import com.patientsvc.service.MyUserDetailService;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	
	private final MyUserDetailService userDetailsSvc;
	private final JwtRequestFilter jwtRequestFilter;
	
	
	
	public SecurityConfiguration(MyUserDetailService userDetailsSvc, JwtRequestFilter jwtRequestFilter) {
		super();
		this.userDetailsSvc = userDetailsSvc;
		this.jwtRequestFilter = jwtRequestFilter;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService(userDetailsSvc);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable().authorizeRequests()
		.antMatchers("/patients/authenticate").permitAll()
		.antMatchers("/v2/api-docs",
					"/configuration/ui",
					"/swagger-resources/**",
					"/configuration/security",
					"/swagger-ui/**",
					"/webjars/**").permitAll()
		.antMatchers("/actuator/**").permitAll()
		.anyRequest().authenticated()
		.and().sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception
	{
		return super.authenticationManagerBean();
	}
	
}
