
package com.usersvc.service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import io.opentracing.Span;
import io.opentracing.Tracer;

import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.javers.spring.annotation.JaversAuditable;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usersvc.aspect.Loggable;
import com.usersvc.dto.UserDto;
import com.usersvc.models.User;
import com.usersvc.repository.IUserRepository;

import lombok.extern.slf4j.Slf4j;


// TODO: Auto-generated Javadoc
/**
 * The Class UserServiceImpl.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService{
	
	/** The index. */
	private final String INDEX="userdata";
	
	/** The type. */
	private final String TYPE = "users";
	
	/** The object mapper. */
	ObjectMapper objectMapper = new ObjectMapper();
	
    /** The rest high level client. */
    private final RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("elasticsearch",9200,"http"), new HttpHost("elasticsearch",9300,"http")));
	
	/** The user repository. */
	private final IUserRepository userRepository;
	
	/** The model mapper. */
	private final ModelMapper modelMapper; 
	
	/** The tracer. */
	private Tracer tracer;

	
	/**
	 * Instantiates a new user service impl.
	 *
	 * @param userRepository the user repository
	 * @param modelMapper the model mapper
	 * @param tracer the tracer
	 * @param roleRepository the role repository
	 */
	public UserServiceImpl(IUserRepository userRepository,ModelMapper modelMapper, Tracer tracer)
	{
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
		this.tracer = tracer;	
	}
	
	/** The entity manager. */
	@PersistenceContext
	private EntityManager entityManager;
	

	/**
	 * Gets the all users.
	 *
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the all users
	 */
	//Get all users
	@Override
	@Transactional(readOnly = true)
	@Loggable
	public List<UserDto> getAllUsers(int pageNo, int pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<User> pagedResult = userRepository.findAll(paging);
		List<User> userList = pagedResult.getContent();
		Type listType = new TypeToken<List<UserDto>>() {}.getType();
		List<UserDto> userDtoList =  modelMapper.map(userList, listType);
		return userDtoList;
	
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
	//Get all users - filter with name
	@Override
	@Transactional(readOnly = true)
	@Loggable
	public List<UserDto> getUsersWithNameFilter(String query,int pageNo, int pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<User> pagedResult = userRepository.findAllByUsernameContains(query, paging);
		List<User> userList = pagedResult.getContent();
		Type listType = new TypeToken<List<UserDto>>() {}.getType();
		List<UserDto> userDtoList =  modelMapper.map(userList, listType);
		return userDtoList;
	}
	
	
	/**
	 * Gets the user by id.
	 *
	 * @param id the id
	 * @return the user by id
	 */
	//Get user by id
	@Transactional(readOnly = true)
	@Loggable
	public UserDto getUserById(long id)
	{
		User userDetails =  userRepository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException());
		UserDto userDto = modelMapper.map(userDetails, UserDto.class);
		return userDto;
	}
	
	//Add new user
	/**
	 * Adds the user.
	 *
	 * @param user the user
	 * @return the user dto
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//@Transactional(propagation = Propagation.REQUIRED)
	@Loggable
	@JaversAuditable
	public UserDto addUser(User user) throws IOException
	{
		
		User userEntity =  userRepository.save(user);
		Map<String, Object> dataMap = objectMapper.convertValue(userEntity, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE,String.valueOf(userEntity.getId())).source(dataMap);
		IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		log.info("resut --> "+response.getResult());
		UserDto myUserDto = modelMapper.map(userEntity, UserDto.class);
		return myUserDto;
	}
	
	/**
	 * Update user.
	 *
	 * @param updatedUser the updated user
	 * @param id the id
	 * @return the user dto
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//update existing user
	@Loggable
	@JaversAuditable
	@SuppressWarnings("unchecked")
	/*@Transactional(propagation = Propagation.REQUIRES_NEW,
					rollbackFor = Exception.class,
					noRollbackFor = EntityNotFoundException.class)
	*/
	public UserDto updateUser(User updatedUser,long id) throws IOException
	{
		    //User user = modelMapper.map(updatedUser, User.class);
		    User userEntity = userRepository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException());;
		    userEntity.setRoles(updatedUser.getRoles());
		    userEntity.setUsername(updatedUser.getUsername());
		    userEntity.setEmail(updatedUser.getEmail());
		    userEntity.setPhonenumber(updatedUser.getPhonenumber());
		    userEntity.setAddress(updatedUser.getAddress());
		    userEntity.setId(id);
			userRepository.save(userEntity);
			UserDto myUserDto = modelMapper.map(userEntity, UserDto.class);
			
			//Elastic search
			Map<String, Object> parameters = objectMapper.convertValue(userEntity, Map.class);
			UpdateByQueryRequest request = new UpdateByQueryRequest(INDEX);
			request.setConflicts("proceed");
			request.setQuery(new TermQueryBuilder("Id",id));
			request.setScript(new Script(ScriptType.INLINE,"painless","ctx._source.putAll(params)",parameters));
			BulkByScrollResponse response = restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
		    log.info("elastic search update response {}", String.valueOf(response.getUpdated()));
		 
		    return myUserDto;
		
	}
	
	/**
	 * Deleter user.
	 *
	 * @param id the id
	 * @param rootSpan the root span
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//delete user
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW,
			rollbackFor = Exception.class,
			noRollbackFor = EntityNotFoundException.class)
	public void  deleterUser(long id,Span rootSpan) throws IOException
	{
		Span span = tracer.buildSpan("service delete user").asChildOf(rootSpan).start();
		userRepository.deleteById(id);
		span.finish();
		DeleteByQueryRequest request = new DeleteByQueryRequest(INDEX);
		request.setConflicts("proceed");
		request.setQuery(new TermQueryBuilder("Id",id));
		BulkByScrollResponse response =  restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
		log.info("elastic search delete response --> {}", String.valueOf(response.getDeleted()));
	}


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
	@Override
	@Loggable
	@Transactional(readOnly = true)
	public List<UserDto> getUsersWithMultipleFilter(String username, String role, String email, int pageNo, int pageSize,
			String sortBy) {
		
	
		/*
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery query = cb.createQuery(User.class);
		Root<User> userRoot = query.from(User.class);
		
		List filterPredicates = new ArrayList<>();
		if(username != null && !username.isEmpty())
		{
			filterPredicates.add(cb.equal(userRoot.get("username"), username));	
		}
		if(role !=null && !role.isEmpty())
		{
			filterPredicates.add(cb.equal(userRoot.get("role"), role));
		}
		if(email != null && !email.isEmpty())
		{
			filterPredicates.add(cb.equal(userRoot.get("email"), email));
		}
		
		query.where(cb.and((Predicate[]) filterPredicates.toArray(new Predicate[filterPredicates.size()])));
		query.orderBy(cb.desc(userRoot.get("Id")));
		List<Object> userList = entityManager.createQuery(query).setFirstResult(pageNo).setMaxResults(pageSize).getResultList();
		if(userList.size()>0)
		{
			Type listType = new TypeToken<List<UserDto>>() {}.getType();
			userDtoList =  modelMapper.map(userList, listType);
		}
		*/
		return null;
		
	}



	/**
	 * Gets the users with email id filter.
	 *
	 * @param query the query
	 * @param pageNo the page no
	 * @param pageSize the page size
	 * @param sortBy the sort by
	 * @return the users with email id filter
	 */
	//Query with Named Queries
	@Override
	@Loggable
	@Transactional(readOnly = true)
	public List<UserDto> getUsersWithEmailIdFilter(String query, int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub

		/*
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		Query q = entityManager.createNamedQuery("User.FindByEmail");
		q.setParameter(1, query);
		List<User> userList=q.getResultList();
		if(userList.size()>0)
		{
			Type listType = new TypeToken<List<UserDto>>() {}.getType();
			userDtoList =  modelMapper.map(userList, listType);
		}
	*/
		return null;

	}


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
	//Query with Named Queries
	@Override
	@Loggable
	@Transactional(readOnly = true)
	//@SuppressWarnings("unchecked")
	public List<UserDto> getUsersWithRoleAndUserNameFilter(String username, String role, int pageNo, int pageSize,
			String sortBy) {
		
		/*
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		Query q = entityManager.createNamedQuery("User.FindByRoleAndUsername");
		q.setParameter(1, role);
		q.setParameter(2, username);
		List<User> userList=q.getResultList();
		if(userList.size()>0)
		{
			Type listType = new TypeToken<List<UserDto>>() {}.getType();
			userDtoList =  modelMapper.map(userList, listType);
		}
		*/
		return null;
	}


	/**
	 * Execute elastic search query.
	 *
	 * @param query the query
	 * @return the map
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public Map<String, Object> executeElasticSearchQuery(String query) throws IOException {
		
		
		Map<String, Object> sourceAsMap = new HashMap<String,Object>();
		StringBuilder sb = new StringBuilder(query);
	    SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.queryStringQuery(sb.toString()));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        log.info("search response --> {} ", searchResponse.status());
        SearchHits hits = searchResponse.getHits();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits)
        {
   
        	 sourceAsMap = hit.getSourceAsMap();
        }
		return sourceAsMap;
	}

}
