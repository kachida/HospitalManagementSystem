package com.usersvc.test.controller;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.usersvc.service.IUserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {
	
	@Autowired
	public MockMvc mockMvc;
	
	@MockBean
	IUserService userService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Test
	public void contextLoads() throws Exception {
       log.info(String.valueOf(mockMvc.getDispatcherServlet()));
    }
	
	@Test
	public void createUserTest() throws Exception
	{
		
	//	Role role = new Role(1,"Doctor");
		
	}

    
    
	

}
