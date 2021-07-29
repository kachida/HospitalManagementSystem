package com.usersvc.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;

import com.usersvc.dto.UserDto;
import com.usersvc.models.User;
import com.usersvc.repository.IUserRepository;
import com.usersvc.service.IUserService;

/**
 * The Class UserServiceTest.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@SpringBootTest	
public class UserServiceTest {
	
	
	@Autowired
	private IUserService userService;
	
	@MockBean(answer=Answers.RETURNS_DEFAULTS)
	private IUserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Test
	public void createUserTest() throws Exception {

		User user = new User(1, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9500853473", "Chennai",
				"Admin");
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user,userService.addUser(user));

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
		long userId = 1;
		User user = new User(userId, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9500853473", "Chennai",
				"Admin");
		UserDto userDto = modelMapper.map(user, UserDto.class);
		when(userService.getUserById(Long.valueOf(1))).thenReturn(userDto);
		assertEquals(userDto,userService.getUserById(userId));

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
		User updatedUser = new User(1, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9000050000", "Chennai",
				"Admin");
	    when(userRepository.save(updatedUser)).thenReturn(updatedUser);
	    UserDto userDto = modelMapper.map(updatedUser, UserDto.class);
	    assertEquals(userDto, userService.updateUser(updatedUser, 1));
        
	}
	
	/**
	 * Delete user test.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void deleteUserTest() throws Exception
	{
		long userId =1;
		userService.deleterUser(1,null);
		verify(userRepository, times(1)).deleteById(userId);
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
		User user1 = new User(1, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9500853473", "Chennai",
				"Admin");
		User user2 = new User(2, "Doctor", "Rhino", "Rhino31@gmail.com", "9500853473", "Chennai",
				"Admin");
		List<User> userList = new ArrayList<User>(Stream.of(user1, user2).collect(Collectors.toList()));
		Page<User> userPage = new PageImpl<>(userList);
		
		Sort.Direction sortDirection = Sort.Direction.DESC;
		Pageable pageable = PageRequest.of(0,3,Sort.by(sortDirection, "username"));
		when(userRepository.findAll(pageable)).thenReturn(userPage);
		
		List<UserDto> userDtoList = userList.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
		assertEquals(userDtoList, userService.getAllUsers(0, 2, "desc"));
		
	}
	
}
