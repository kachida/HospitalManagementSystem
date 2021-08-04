package com.usersvc.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import com.google.common.collect.ImmutableMap;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.usersvc.aspect.Loggable;
import com.usersvc.dto.UserDto;
import com.usersvc.models.AuthenticationRequest;
import com.usersvc.models.AuthenticationResponse;
import com.usersvc.models.User;
import com.usersvc.security.JwtUtil;
import com.usersvc.service.IUserService;
import com.usersvc.service.MyUserDetailService;

import io.micrometer.core.instrument.MeterRegistry;
import io.opentracing.Span;
import io.opentracing.Tracer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;



// TODO: Auto-generated Javadoc
/**
 * UserController.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@RestController
@RequestMapping("/users")
@Api(value="UserController", description="Operations pertaining to users in user module API")
public class UserController {
	

	
	/** The meter registry. */
	private final MeterRegistry meterRegistry;
	
	/** The user service. */
	private final IUserService userService;
	
	/** The authentication manager. */
	private final AuthenticationManager authenticationManager;
	
	/** The user details service. */
	private final MyUserDetailService userDetailsService;
	
	/** The jwt util token. */
	private final JwtUtil jwtUtilToken;
	
	/** The tracer. */
	private Tracer tracer;
	
	/** The model mapper. */
	private ModelMapper modelMapper;
	
	/**
	 * Instantiates a new user controller.
	 *
	 * @param meterRegistry the meter registry
	 * @param userService the user service
	 * @param authenticationManager the authentication manager
	 * @param userDetailsService the user details service
	 * @param jwtUtilToken the jwt util token
	 * @param tracer the tracer
	 * @param modelMapper the model mapper
	 */
	public UserController(MeterRegistry meterRegistry,IUserService userService,AuthenticationManager authenticationManager,MyUserDetailService userDetailsService, JwtUtil jwtUtilToken, Tracer tracer,ModelMapper modelMapper)
	{
		this.meterRegistry= meterRegistry;
		this.userService= userService;
		this.authenticationManager= authenticationManager;
		this.tracer= tracer;
		this.userDetailsService = userDetailsService;
		this.jwtUtilToken = jwtUtilToken;
		this.modelMapper = modelMapper ;
	}
	
	/**
	 * Gets the all users.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the all users
	 */
	//fetch all users pagination supported
	@GetMapping("/")
	@Loggable
	@ApiOperation(value = "Retrieve All Users with pagination and sorting supported", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public List<UserDto> getAllUsers(
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="Id") String sortBy)
	{
		//Custom Metrics added to get the API Method execution time and Api request count
		Instant start = Instant.now();
		List<UserDto> userList =  userService.getAllUsers(pageNo, pageSize, sortBy);
		Instant stop = Instant.now();
		meterRegistry.counter("usersvc.getallusers.count").increment();
		meterRegistry.timer("usersvc.getallusers.executiontime").record(Duration.between(start, stop).toMillis(),TimeUnit.MILLISECONDS);
		return userList;
		
	}
	
	/**
	 * Gets the user by id.
	 *
	 * @param id the id
	 * @return the user by id
	 */
	//fetch user by id
	@GetMapping("/{id}")
	@Loggable
	@ApiOperation(value = "Retrieve user details with given id", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token"),
	})
	public UserDto getUserById(@PathVariable long id)
	{

		UserDto userDetails =  userService.getUserById(id);
		return userDetails;
	}
	
	/**
	 * Gets the users with name filter.
	 *
	 * @param query the query
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the users with name filter
	 */
	//filter and fetch users based on single field i.e, username using spring jpa filtering
	@GetMapping("/search")
	@Loggable
	@ApiOperation(value = "Retrieve all users with given username", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public List<UserDto> getUsersWithNameFilter(@RequestParam("query") String query,
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="Id") String sortBy)
	{
		
		List<UserDto> userList = userService.getUsersWithNameFilter(query, pageNo, pageSize, sortBy);
		return userList;
	}
	
	/**
	 * Gets the users with email filter.
	 *
	 * @param email the email
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the users with email filter
	 */
	//Fetch users based on emailid - using namedqueries 
		@GetMapping("/filter/emailid")
		@Loggable
		@ApiOperation(value = "Retrieve all users with given email", produces = "application/json")
		@ApiResponses(value = {
		        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
		        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
		})
		public List<UserDto> getUsersWithEmailFilter(
				@RequestParam("email") String email,
				@RequestParam(defaultValue="0") int pageNo,
				@RequestParam(defaultValue="10") int pageSize,
				@RequestParam(defaultValue="Id") String sortBy)
		{
			List<UserDto> userList = userService.getUsersWithEmailIdFilter(email, pageNo, pageSize, sortBy);
			return userList;
		}
	
	/**
	 * Gets the users with name role email filter.
	 *
	 * @param username the username
	 * @param role the role
	 * @param email the email
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the users with name role email filter
	 */
	//filter and fetch users based on multiple fields (username, email,role) - using criteria API 
	@GetMapping("/filter")
	@Loggable
	@ApiOperation(value = "Retrieve all users with given username, email and role", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public List<UserDto> getUsersWithNameRoleEmailFilter(@RequestParam("name") String username,
			@RequestParam("role") String role,
			@RequestParam("email") String email,
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="Id") String sortBy)
	{
		List<UserDto> userList = userService.getUsersWithMultipleFilter(username,role,email, pageNo, pageSize, sortBy);
		return userList;
	}
	
	
	/**
	 * Gets the users with name role filter.
	 *
	 * @param username the username
	 * @param role the role
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the users with name role filter
	 */
	//Filter and fetch users based on multiple fields i.e, username and role using namedqueries)
	@GetMapping("/filter/roleAndName")
	@Loggable
	@ApiOperation(value = "Retrieve all users with given username, email and role", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public List<UserDto> getUsersWithNameRoleFilter(@RequestParam("name") String username,
			@RequestParam("role") String role,
			@RequestParam(defaultValue="0") int pageNo,
			@RequestParam(defaultValue="10") int pageSize,
			@RequestParam(defaultValue="Id") String sortBy)
	{
		List<UserDto> userList = userService.getUsersWithRoleAndUserNameFilter(username,role,pageNo, pageSize, sortBy);
		return userList;
	}
	
	/**
	 * Save user.
	 *
	 * @param userDto the user dto
	 * @return the user dto
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//create new user
	@PostMapping("/")
	@Loggable
	@ApiOperation(value = "To create a new user", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Successfully the record is created"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public UserDto saveUser(@RequestBody UserDto userDto) throws IOException
	{
		User user = modelMapper.map(userDto, User.class);
		return userService.addUser(user); 
	}
	
	/**
	 * Update user.
	 *
	 * @param user the user
	 * @param id the id
	 * @return the user dto
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//update existing user
	@PutMapping("/{id}")
	@Loggable
	@ApiOperation(value = "To update the existing  user", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully the record is updated"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public UserDto updateUser(@RequestBody UserDto user,@PathVariable long id) throws IOException
	{
		User userEntity = modelMapper.map(user, User.class);
		UserDto userDetails = userService.updateUser(userEntity,id);
		return userDetails;
	}
	
	/**
	 * Delete user.
	 *
	 * @param id the id
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//delete user
	@DeleteMapping("/{id}")
	@Loggable
	@ApiOperation(value = "To delete the existing  user", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully the record is deleted"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public void deleteUser(@PathVariable long id) throws IOException
	{
		Span span = tracer.buildSpan("delete user").start();
        span.log(ImmutableMap.of("event", "delete-request", "value", String.valueOf(id)));
		userService.deleterUser(id,span);
		span.finish();
	}
	
	/**
	 * Creates the authentication token.
	 *
	 * @param authenticationRequest the authentication request
	 * @return the response entity
	 * @throws Exception the exception
	 */
	//Authenticate
	@PostMapping("/authenticate")
	@Loggable
	@ApiOperation(value = "To generate a new JWT Token", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully the jwt token is generated"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check input credentials")
	})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception
	{
		authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtilToken.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
		
	}
	
	
	/**
	 * Execute elastic search query.
	 *
	 * @param query the query
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@GetMapping("/executeElasticSearchQuery")
	@Loggable
	public Map<String, Object> executeElasticSearchQuery(@RequestParam(name = "q") String query) throws IOException
	{
		return userService.executeElasticSearchQuery(query);
	}
	
	
	@GetMapping("/getAllUsersCreatedInDateRange")
	@Loggable
	public Page<User> getAllUsersCreatedInDateRange(@RequestParam(required = false) LocalDateTime fromDate, @RequestParam(required = false) LocalDateTime endDate,  @RequestParam(required = false) Integer page,  @RequestParam(required = false) Integer size )
	{
	    return userService.getAllUsersCreatedInDateRange(fromDate, endDate, page,size);
	}
	

}
