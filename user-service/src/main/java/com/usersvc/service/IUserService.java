package com.usersvc.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import com.usersvc.dto.UserDto;
import com.usersvc.models.User;

import io.opentracing.Span;



// TODO: Auto-generated Javadoc
/**
 * The Interface IUserService.
 *
 * @author : Kannappan
 * @version : 1.0
 */
public interface IUserService {
	
	/**
	 * Gets the all users.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the all users
	 */
	public List<UserDto> getAllUsers(int pageNo, int pageSize, String sortBy);
	
	/**
	 * Gets the users with name filter.
	 *
	 * @param query the query
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the users with name filter
	 */
	public List<UserDto> getUsersWithNameFilter(String query,int pageNo, int pageSize, String sortBy);
	
	/**
	 * Gets the users with multiple filter.
	 *
	 * @param username the username
	 * @param role the role
	 * @param email the email
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the users with multiple filter
	 */
	public List<UserDto> getUsersWithMultipleFilter(String username, String role, String email,int pageNo, int pageSize, String sortBy);
	
	/**
	 * Gets the user by id.
	 *
	 * @param id the id
	 * @return the user by id
	 */
	public UserDto getUserById(long id);
	
	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @return the user dto
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public UserDto addUser(User user) throws IOException;
	
	/**
	 * Update user.
	 *
	 * @param updatedUser the updated user
	 * @param id the id
	 * @return the user dto
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public UserDto updateUser(User updatedUser,long id) throws IOException;
	
	/**
	 * Deleter user.
	 *
	 * @param id the id
	 * @param span the span
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void deleterUser(long id,Span span) throws IOException;
	
	/**
	 * Gets the users with email id filter.
	 *
	 * @param query the query
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the users with email id filter
	 */
	public List<UserDto> getUsersWithEmailIdFilter(String query,int pageNo, int pageSize, String sortBy);
	
	/**
	 * Gets the users with role and user name filter.
	 *
	 * @param username the username
	 * @param role the role
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the users with role and user name filter
	 */
	public List<UserDto> getUsersWithRoleAndUserNameFilter(String username,String role,int pageNo, int pageSize, String sortBy);
	
	/**
	 * Execute elastic search query.
	 *
	 * @param query the query
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Map<String, Object> executeElasticSearchQuery(String query) throws IOException; 
	
	
	public Page<User> getAllUsersCreatedInDateRange(LocalDateTime fromDate, LocalDateTime endDate, Integer page, Integer size );
	

}
