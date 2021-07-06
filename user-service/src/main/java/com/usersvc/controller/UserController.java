package com.usersvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.usersvc.models.User;
import com.usersvc.service.IUserService;


@RestController
@RequestMapping("/usersvc")
public class UserController {
	

	
	@Autowired
	private IUserService userService;
	
	//fetch all users pagination supported
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="Id") String sortBy)
	{
		List<User> userList =  userService.getAllUsers(pageNo, pageSize, sortBy);
		
		return new ResponseEntity<List<User>>(userList, new HttpHeaders(), HttpStatus.OK);
		
	}
	
	//fetch user by id
	@GetMapping("/users/{id}")
	public User getUserById(@PathVariable long id)
	{
		return userService.getUserById(id);
	}
	
	//filter and fetch users based on single field i.e, username using spring jpa filtering
	@GetMapping("/users/search")
	public ResponseEntity<List<User>> getUsersWithNameFilter(@RequestParam("query") String query,
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="Id") String sortBy)
	{
		List<User> userList = userService.getUsersWithNameFilter(query, pageNo, pageSize, sortBy);
		return new ResponseEntity<List<User>>(userList,new HttpHeaders(), HttpStatus.OK);
	}
	
	//filter and fetch users based on multiple fields (username, email,role) - using criteria API 
	@GetMapping("/users/filter")
	public ResponseEntity<List<User>> getUsersWithNameFilter(@RequestParam("name") String username,
			@RequestParam("role") String role,
			@RequestParam("email") String email,
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="Id") String sortBy)
	{
		List<User> userList = userService.getUsersWithMultipleFilter(username,role,email, pageNo, pageSize, sortBy);
		return new ResponseEntity<List<User>>(userList,new HttpHeaders(), HttpStatus.OK);
	}
	
	//Fetch users based on emailid - using namedqueries 
	@GetMapping("/users/filter/emailid")
	public ResponseEntity<List<User>> getUsersWithEmailFilter(
			@RequestParam("email") String email,
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="Id") String sortBy)
	{
		List<User> userList = userService.getUsersWithEmailIdFilter(email, pageNo, pageSize, sortBy);
		return new ResponseEntity<List<User>>(userList,new HttpHeaders(), HttpStatus.OK);
	}
	
	//Filter and fetch users based on multiple fields i.e, username and role using namedqueries)
	@GetMapping("/users/filter/roleAndName")
	public ResponseEntity<List<User>> getUsersWithNameFilter(@RequestParam("name") String username,
			@RequestParam("role") String role,
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="Id") String sortBy)
	{
		List<User> userList = userService.getUsersWithRoleAndUserNameFilter(username,role,pageNo, pageSize, sortBy);
		return new ResponseEntity<List<User>>(userList,new HttpHeaders(), HttpStatus.OK);
	}
	
	//create new user
	@PostMapping("/users")
	public void saveUser(@RequestBody User user)
	{
		 userService.addUser(user);
	}
	
	//update existing user
	@PutMapping("/users/{id}")
	public void updateUser(@RequestBody User user,@PathVariable long id)
	{
		userService.updateUser(user,id);
	}
	
	//delete user
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable long id)
	{
		userService.deleterUser(id);
	}

}
