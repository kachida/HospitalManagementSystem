package com.patientsvc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.patientsvc.models.User;

@FeignClient(name="user-service", url ="http://user-service:8081", configuration = UserServiceClientConfiguration.class)
public interface UserServiceClient {

	@RequestMapping(value="/usersvc/users/{id}", method = RequestMethod.GET)
	User getUserById(@PathVariable long id);
	
}
