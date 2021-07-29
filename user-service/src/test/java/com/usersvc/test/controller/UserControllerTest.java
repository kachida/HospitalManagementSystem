package com.usersvc.test.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.usersvc.dto.UserDto;
import com.usersvc.models.User;
import com.usersvc.service.IUserService;

import lombok.extern.slf4j.Slf4j;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: Auto-generated Javadoc
/**
 * The Class UserControllerTest.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@SpringBootTest
@AutoConfigureMockMvc

/** The Constant log. */
@Slf4j
public class UserControllerTest {

	/** The mock mvc. */
	@Autowired
	public MockMvc mockMvc;

	/** The user service. */
	@MockBean
	IUserService userService;

	/** The model mapper. */
	@Autowired
	ModelMapper modelMapper;

	/**
	 * Context loads.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void contextLoads() throws Exception {
		log.info(String.valueOf(mockMvc.getDispatcherServlet()));
	}

	/**
	 * Creates the user test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void createUserTest() throws Exception {

		User user = new User(1, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9500853473", "Chennai",
				"Admin");
		UserDto userDto = modelMapper.map(user, UserDto.class);
		when(userService.addUser(user)).thenReturn(userDto);
		MockHttpServletResponse httpResponse = this.mockMvc.perform(post("/users").content(
				"{    \"username\":\"Rhino56\",    \"email\":\"Rhino56@gmail.com\",    \"phonenumber\":\"9500853473\",    \"address\":\"No.9 Vasugi Street, Poothapedu, Ramapuram , Chennai - 6000089\",    \"roles\":\"Doctor\" }")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.email").value("Rhino56@gmail.com"))
				.andExpect(status().isCreated()).andReturn().getResponse();
		log.info("create user test response: {}",httpResponse.getContentAsString());

	}
	
	/**
	 * Gets the user test.
	 *
	 * @return the user details
	 * @throws Exception the exception
	 */
	@Test
	public void getUserTest() throws Exception
	{
		User user = new User(1, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9500853473", "Chennai",
				"Admin");
		UserDto userDto = modelMapper.map(user, UserDto.class);
		when(userService.getUserById(Long.valueOf(1))).thenReturn(userDto);
		MockHttpServletResponse httpResponse = this.mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse();
		log.info("Get user By Id test response: {}",httpResponse.getContentAsString());
	}
	
	/**
	 * Update user test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void updateUserTest() throws Exception
	{
		User user = new User(1, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9500853473", "Chennai",
				"Admin");
		UserDto userDto = modelMapper.map(user, UserDto.class);
		when(userService.updateUser(user, 1)).thenReturn(userDto);
        MockHttpServletResponse httpResponse = this.mockMvc.perform(put("/users")
        		.content("{    \"username\":\"Rhino56\",    \"email\":\"Rhino56@gmail.com\",    \"phonenumber\":\"9000050000\",    \"address\":\"No.9 Vasugi Street, Poothapedu, Ramapuram , Chennai - 6000089\",    \"roles\":\"Doctor\" }")
        		.contentType(MediaType.APPLICATION_JSON))
        		.andExpect(jsonPath("$.phonenumber").value("9000050000"))
        		.andExpect(status().isOk()).andReturn().getResponse();
        log.info("update test response: {}",httpResponse.getContentAsString());
        
	}
	
	/**
	 * Delete user test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteUserTest() throws Exception
	{
		MockHttpServletResponse httpResponse = this.mockMvc.perform(delete("/users/1")
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isNoContent()).andReturn().getResponse();
		log.info("delete test response: {}",httpResponse.getContentAsString());
	}
	
	/**
	 * Gets the all users test.
	 *
	 * @return  all users 
	 * @throws Exception the exception
	 */
	@Test
	public void getAllUsersTest() throws Exception
	{
		MockHttpServletResponse httpResponse = this.mockMvc.perform(get("/users/")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse();
		log.info("get all users test response: {}",httpResponse.getContentAsString());
	}
	
	

	/**
	 * Elastic search test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void elasticSearchTest() throws Exception
	{
		MockHttpServletResponse httpResponse = this.mockMvc.perform(get("/executeElasticSearchQuery")
				.param("q", "Rhino")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse();
		
		log.info("elastic search test response: {}",httpResponse.getContentAsString());
	}
	
	
}
