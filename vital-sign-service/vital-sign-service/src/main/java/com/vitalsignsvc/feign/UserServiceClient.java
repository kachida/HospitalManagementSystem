package com.vitalsignsvc.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.vitalsignsvc.models.User;

@FeignClient(name="user-svc-client" , url="http://localhost:8081", configuration = UserServiceClientConfiguration.class)
public interface UserServiceClient {

	@GetMapping("/usersvc/users/{id}")
	User getUserById(@PathVariable long id);
	
}
