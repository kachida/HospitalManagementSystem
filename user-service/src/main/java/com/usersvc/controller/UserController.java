package com.usersvc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usersvc.models.AuthenticationRequest;
import com.usersvc.models.AuthenticationResponse;
import com.usersvc.models.User;
import com.usersvc.security.JwtUtil;
import com.usersvc.service.IUserService;
import com.usersvc.service.MyUserDetailService;


@RestController
@RequestMapping("/usersvc")
public class UserController {
	

	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailService userDetailsService;
	
	@Autowired
	private JwtUtil jwtUtilToken;
	
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
	public ResponseEntity<User> getUserById(@PathVariable long id)
	{
		Optional<User> userDetails =  userService.getUserById(id);
		if(userDetails.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(userDetails.get());
		}else
		{
			return ResponseEntity.status(HttpStatus.OK).body(userDetails.get());
		}
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
	public ResponseEntity<User> saveUser(@RequestBody User user)
	{
		return new ResponseEntity<User>(userService.addUser(user),HttpStatus.CREATED); 
	}
	
	//update existing user
	@PutMapping("/users/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user,@PathVariable long id)
	{
		Optional<User> userDetails = userService.updateUser(user,id);
		if(userDetails.isEmpty())
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(user);
		}
		else
		{
			return ResponseEntity.status(HttpStatus.OK).body(userDetails.get());
		}
	}
	
	//delete user
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable long id)
	{
		userService.deleterUser(id);
	}
	
	//Authenticate
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
	{
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtilToken.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}
	

}
