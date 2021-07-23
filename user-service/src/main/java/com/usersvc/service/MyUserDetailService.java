package com.usersvc.service;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.usersvc.models.YamlProperties;

@Service
public class MyUserDetailService implements UserDetailsService {
	
	
	private final YamlProperties yamlProps;
	
	public MyUserDetailService(YamlProperties yamlProps)
	{
		this.yamlProps = yamlProps;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return new User(yamlProps.getUsername(),yamlProps.getPassword(),new ArrayList<>());
	}

	
	
}
