package com.usersvc.service;

import java.io.IOException;
import java.util.List;


import org.springframework.web.bind.annotation.RequestParam;

import com.usersvc.dto.UserDto;

public interface IUserService {
	
	public List<UserDto> getAllUsers(int pageNo, int pageSize, String sortBy);
	public List<UserDto> getUsersWithNameFilter(String query,int pageNo, int pageSize, String sortBy);
	public List<UserDto> getUsersWithMultipleFilter(String username, String role, String email,int pageNo, int pageSize, String sortBy);
	public UserDto getUserById(long id);
	public UserDto addUser(UserDto user) throws IOException;
	public UserDto updateUser(UserDto updatedUser,long id) throws IOException;
	public void deleterUser(long id) throws IOException;
	public List<UserDto> getUsersWithEmailIdFilter(String query,int pageNo, int pageSize, String sortBy);
	public List<UserDto> getUsersWithRoleAndUserNameFilter(String username,String role,int pageNo, int pageSize, String sortBy);
	public List<UserDto> executeElasticSearchQuery(@RequestParam(name = "q") String query) throws IOException; 
	
	

}
