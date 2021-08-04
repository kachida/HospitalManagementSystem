package com.usersvc.test.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.usersvc.models.User;
import com.usersvc.models.YamlProperties;
import com.usersvc.repository.IUserRepository;
import com.usersvc.service.IUserService;
import com.usersvc.service.UserServiceImpl;

import io.opentracing.Tracer;

/**
 * The Class UserServiceTest.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@SpringBootTest	
@AutoConfigureMockMvc
public class UserServiceTest {
	
	
	@Autowired
	private UserServiceImpl userService;
	
	@MockBean(answer=Answers.RETURNS_DEFAULTS)
	private IUserRepository userRepository;
	
	private Tracer tracer;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private final String TYPE = "users";
	private final String INDEX="userdata";
	
	@Autowired
	public MockMvc mockMvc;
	
	private RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200,"http"), new HttpHost("localhost",9300,"http")));
	
	@Autowired
	private YamlProperties yamlProps;
	
	private String token;
	
	@Before
	public void setup()
	{
		restHighLevelClient = mock(RestHighLevelClient.class);
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

		
		
		User user = new User(1, "Doctor", "Kannappan", "kannappanchidambaram@gmail.com", "9500853473", "Chennai",
				"Admin");
		//when( new IndexRequest(INDEX, TYPE,String.valueOf(userEntity.getId())).source(dataMap));
		when(restHighLevelClient.index(new IndexRequest(), RequestOptions.DEFAULT)).thenReturn(null);
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user,userService.addUser(user));

	}
	
	/*

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
	

	@Test
	public void deleteUserTest() throws Exception
	{
		long userId =1;
		userService.deleterUser(1,null);
		verify(userRepository, times(1)).deleteById(userId);
	}

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
	
	*/
	
}
