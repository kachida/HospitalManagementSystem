package com.usersvc.service;

import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.usersvc.models.YamlProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class MyUserDetailService.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Service
public class MyUserDetailService implements UserDetailsService {
	
	
	/** The yaml props. */
	private final YamlProperties yamlProps;
	
	/**
	 * Instantiates a new my user detail service.
	 *
	 * @param yamlProps the yaml props
	 */
	public MyUserDetailService(YamlProperties yamlProps)
	{
		this.yamlProps = yamlProps;
	}

	/**
	 * Load user by username.
	 *
	 * @param username the username
	 * @return the user details
	 * @throws UsernameNotFoundException the username not found exception
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return new User(yamlProps.getUsername(),yamlProps.getPassword(),new ArrayList<>());
	}

	
	
}
