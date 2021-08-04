package com.usersvc.test.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.usersvc.dto.UserDto;
import com.usersvc.models.User;
import com.usersvc.models.YamlProperties;
import com.usersvc.service.IUserService;

import lombok.extern.slf4j.Slf4j;

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

	@Autowired
	private YamlProperties yamlProps;
	
	private String token;
	/**
	 * Context loads.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void contextLoads() throws Exception {
		log.info(String.valueOf(mockMvc.getDispatcherServlet()));
	}
	
	public String getToken() throws Exception{
		
		String username = yamlProps.getUsername();
		String password = yamlProps.getPassword();
		String body="{\"username\":\"" + username+"\", \"password\":\"" + password +"\"}";
		
		MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/users/authenticate").
				content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
		String response = result.getResponse().getContentAsString();
		response = response.replace("{\"jwt\":\"", "");
		token = response.replace("\"}", "").replace("\"", "");
		return token;
		
	}

	
	
	@Test
	public void createUserTest() throws Exception {
		
		getToken();
		User user = new User(1, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9500853473", "Chennai",
				"Admin");
		UserDto userDto = modelMapper.map(user, UserDto.class);
		when(userService.addUser(user)).thenReturn(userDto);
		MockHttpServletResponse httpResponse = this.mockMvc.perform(post("/users/")
				.header("Authorization", "Bearer " + token)
				.content(
				"{    \"username\":\"Rhino56\",    \"email\":\"Rhino56@gmail.com\",    \"phonenumber\":\"9500853473\",    \"address\":\"No.9 Vasugi Street, Poothapedu, Ramapuram , Chennai - 6000089\",    \"roles\":\"Doctor\" }")
				.contentType(MediaType.APPLICATION_JSON))
				//.andExpect(jsonPath("$.email").value("Rhino56@gmail.com"))
				.andExpect(status().isOk()).andReturn().getResponse();
		log.info("create user test response: {}",httpResponse.getContentAsString());

	}
	
	

	
	@Test
	public void getUserTest() throws Exception
	{
	   getToken();
		User user = new User(1, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9500853473", "Chennai",
				"Admin");
		UserDto userDto = modelMapper.map(user, UserDto.class);
		when(userService.getUserById(Long.valueOf(1))).thenReturn(userDto);
		MockHttpServletResponse httpResponse = this.mockMvc.perform(MockMvcRequestBuilders.get("/users/1").header("Authorization", "Bearer " + token).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andReturn().getResponse();
		log.info("Get user By Id test response: {}",httpResponse.getContentAsString());
	}
	

	@Test
	public void updateUserTest() throws Exception
	{
		getToken();
		User user = new User(1, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9500853473", "Chennai",
				"Admin");
		UserDto userDto = modelMapper.map(user, UserDto.class);
		when(userService.updateUser(user, 1)).thenReturn(userDto);
        MockHttpServletResponse httpResponse = this.mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
        		.header("Authorization", "Bearer " + token)
        		.content("{    \"username\":\"Rhino56\",    \"email\":\"Rhino56@gmail.com\",    \"phonenumber\":\"9000050000\",    \"address\":\"No.9 Vasugi Street, Poothapedu, Ramapuram , Chennai - 6000089\",    \"roles\":\"Doctor\" }")
        		.contentType(MediaType.APPLICATION_JSON))
        		//.andExpect(MockMvcResultMatchers.jsonPath("$.phonenumber").value("9000050000"))
        		.andExpect(status().isOk()).andReturn().getResponse();
        log.info("update test response: {}",httpResponse.getContentAsString());
        
	}
	

	@Test
	public void deleteUserTest() throws Exception
	{
		getToken();
		MockHttpServletResponse httpResponse = this.mockMvc.perform(MockMvcRequestBuilders.delete("/users/1")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON))
		        .andExpect(status().isOk()).andReturn().getResponse();
		log.info("delete test response: {}",httpResponse.getContentAsString());
	}
	

	@Test
	public void getAllUsersTest() throws Exception
	{
		getToken();
		MockHttpServletResponse httpResponse = this.mockMvc.perform(MockMvcRequestBuilders.get("/users/")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse();
		log.info("get all users test response: {}",httpResponse.getContentAsString());
	}
	
	

	/*

	@Test
	public void elasticSearchTest() throws Exception
	{
		getToken();
		MockHttpServletResponse httpResponse = this.mockMvc.perform(MockMvcRequestBuilders.get("/executeElasticSearchQuery/")
				.param("q", "Rhino")
				.header("Authorization", "Bearer " + token)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse();
		
		log.info("elastic search test response: {}",httpResponse.getContentAsString());
	}
*/
	
	
}
