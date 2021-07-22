package com.usersvc.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

import com.usersvc.models.User;

public interface IUserService {
	
	public List<User> getAllUsers(int pageNo, int pageSize, String sortBy);
	public List<User> getUsersWithNameFilter(String query,int pageNo, int pageSize, String sortBy);
	public List<User> getUsersWithMultipleFilter(String username, String role, String email,int pageNo, int pageSize, String sortBy);
	public Optional<User> getUserById(long id);
	public User addUser(User user) throws IOException;
	public Optional<User> updateUser(User user,long id) throws IOException;
	public void deleterUser(long id) throws IOException;
	public List<User> getUsersWithEmailIdFilter(String query,int pageNo, int pageSize, String sortBy);
	public List<User> getUsersWithRoleAndUserNameFilter(String username,String role,int pageNo, int pageSize, String sortBy);
	public List<User> executeElasticSearchQuery(@RequestParam(name = "q") String query) throws IOException; 
	
	

}
