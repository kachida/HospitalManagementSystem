package com.usersvc.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.usersvc.dto.UserDto;
import com.usersvc.models.User;

import io.opentracing.Span;

public interface IUserService {
	
	public List<UserDto> getAllUsers(int pageNo, int pageSize, String sortBy);
	public List<UserDto> getUsersWithNameFilter(String query,int pageNo, int pageSize, String sortBy);
	public List<UserDto> getUsersWithMultipleFilter(String username, String role, String email,int pageNo, int pageSize, String sortBy);
	public UserDto getUserById(long id);
	public UserDto addUser(User user) throws IOException;
	public UserDto updateUser(UserDto updatedUser,long id) throws IOException;
	public void deleterUser(long id,Span span) throws IOException;
	public List<UserDto> getUsersWithEmailIdFilter(String query,int pageNo, int pageSize, String sortBy);
	public List<UserDto> getUsersWithRoleAndUserNameFilter(String username,String role,int pageNo, int pageSize, String sortBy);
	public Map<String, Object> executeElasticSearchQuery(@RequestParam(name = "q") String query) throws IOException; 
	
	

}
