package com.usersvc.service;

import java.util.List;

import com.usersvc.models.User;

public interface IUserService {
	
	public List<User> getAllUsers(int pageNo, int pageSize, String sortBy);
	public List<User> getUsersWithNameFilter(String query,int pageNo, int pageSize, String sortBy);
	public List<User> getUsersWithMultipleFilter(String username, String role, String email,int pageNo, int pageSize, String sortBy);
	public User getUserById(long id);
	public User addUser(User user);
	public User updateUser(User user,long id);
	public void deleterUser(long id);
	public List<User> getUsersWithEmailIdFilter(String query,int pageNo, int pageSize, String sortBy);
	public List<User> getUsersWithRoleAndUserNameFilter(String username,String role,int pageNo, int pageSize, String sortBy);
	
	

}
