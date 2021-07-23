package com.usersvc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


@Service
public class UserServiceImpl implements IUserService{
	
	private final String INDEX="userdata";
	 private final String TYPE = "users";
	ObjectMapper objectMapper = new ObjectMapper();
	
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("elasticsearch",9200,"http"), new HttpHost("elasticsearch",9300,"http")));
	private final IUserRepository userRepository;
	private final ModelMapper modelMapper; 
	
	public UserServiceImpl(IUserRepository userRepository,ModelMapper modelMapper )
	{
		this.userRepository = userRepository;
		this.modelMapper = modelMapper;
	}
	
	@PersistenceContext
	private EntityManager entityManager;
	

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
	//@Transactional(propagation = Propagation.REQUIRED)
	@Loggable
	@JaversAuditable
	@SuppressWarnings("unchecked")
	public UserDto addUser(UserDto userDto) throws IOException
	{
		User myUser = modelMapper.map(userDto, User.class);
		User userEntity = userRepository.save(myUser);
		UserDto myUserDto = modelMapper.map(userEntity, UserDto.class);
		//Elastic Search
		Map<String, Object> dataMap = objectMapper.convertValue(userEntity, Map.class);
		IndexRequest indexRequest = new IndexRequest(INDEX, TYPE,String.valueOf(userEntity.getId())).source(dataMap);
		IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
		logger.info("resut --> "+response.getResult());
		return myUserDto;
		
	}
	
	//update existing user
	@Loggable
	@JaversAuditable
	@SuppressWarnings("unchecked")
	/*@Transactional(propagation = Propagation.REQUIRES_NEW,
					rollbackFor = Exception.class,
					noRollbackFor = EntityNotFoundException.class)
	*/
	public UserDto updateUser(UserDto updatedUser,long id) throws IOException
	{
		    User user = modelMapper.map(updatedUser, User.class);
		    User userEntity = userRepository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException());;
		    userEntity.setRole(user.getRole());
		    userEntity.setUsername(user.getUsername());
		    userEntity.setEmail(user.getEmail());
		    userEntity.setPhonenumber(user.getPhonenumber());
		    userEntity.setAddress(user.getAddress());
		    userEntity.setId(id);
			userRepository.save(userEntity);
			UserDto myUserDto = modelMapper.map(userEntity, UserDto.class);
			
			//Elastic search
			Map<String, Object> parameters = objectMapper.convertValue(user, Map.class);
			UpdateByQueryRequest request = new UpdateByQueryRequest(INDEX);
			request.setConflicts("proceed");
			request.setQuery(new TermQueryBuilder("Id",id));
			request.setScript(new Script(ScriptType.INLINE,"painless","ctx._source.putAll(params)",parameters));
			BulkByScrollResponse response = restHighLevelClient.updateByQuery(request, RequestOptions.DEFAULT);
		    logger.info("elastic search update response {}"+ String.valueOf(response.getUpdated()));
		 
		    return myUserDto;
		
	}
	
	//delete user
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW,
			rollbackFor = Exception.class,
			noRollbackFor = EntityNotFoundException.class)
	public void deleterUser(long id) throws IOException
	{
		userRepository.deleteById(id);
		DeleteByQueryRequest request = new DeleteByQueryRequest(INDEX);
		request.setConflicts("proceed");
		request.setQuery(new TermQueryBuilder("Id",id));
		BulkByScrollResponse response =  restHighLevelClient.deleteByQuery(request, RequestOptions.DEFAULT);
		logger.info("elastic search delete response {}"+ String.valueOf(response.getDeleted()));
	}


	@Override
	@Loggable
	@Transactional(readOnly = true)
	@SuppressWarnings({"unchecked","rawtypes"})
	public List<UserDto> getUsersWithMultipleFilter(String username, String role, String email, int pageNo, int pageSize,
			String sortBy) {
		
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
		return userDtoList;
	}



	//Query with Named Queries
	@Override
	@Loggable
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UserDto> getUsersWithEmailIdFilter(String query, int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub

		List<UserDto> userDtoList = new ArrayList<UserDto>();
		Query q = entityManager.createNamedQuery("User.FindByEmail");
		q.setParameter(1, query);
		List<User> userList=q.getResultList();
		if(userList.size()>0)
		{
			Type listType = new TypeToken<List<UserDto>>() {}.getType();
			userDtoList =  modelMapper.map(userList, listType);
		}
		return userDtoList;

	}


	//Query with Named Queries
	@Override
	@Loggable
	@Transactional(readOnly = true)
	@SuppressWarnings("unchecked")
	public List<UserDto> getUsersWithRoleAndUserNameFilter(String username, String role, int pageNo, int pageSize,
			String sortBy) {
		
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
		return userDtoList;
	}


	@Override
	public List<UserDto> executeElasticSearchQuery(String query) throws IOException {
		
		List<UserDto> userDtoList = new ArrayList<UserDto>();
		StringBuilder sb = new StringBuilder(query);
	    SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.queryStringQuery(sb.toString()));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        logger.info("search response --> "+ searchResponse.status());
        SearchHits hits = searchResponse.getHits();
        List<User> userList = new ArrayList<User>();
        SearchHit[] searchHits = hits.getHits();
        for (SearchHit hit : searchHits)
        {
        	User user = new User();
        	Map<String, Object> sourceAsMap = hit.getSourceAsMap();
        	user.setId(Long.parseLong((String) sourceAsMap.get("Id")));       	
        	user.setRole((String) sourceAsMap.get("role"));
        	user.setEmail((String) sourceAsMap.get("email"));
        	user.setUsername((String) sourceAsMap.get("username"));
        	user.setCreatedBy((String) sourceAsMap.get("createdBy"));
        	user.setLastModifiedBy((String) sourceAsMap.get("lastModifiedBy"));
        	userList.add(user);
        }
        if(userList.size()>0)
		{
			Type listType = new TypeToken<List<UserDto>>() {}.getType();
			userDtoList =  modelMapper.map(userList, listType);
		}
		return userDtoList;
	}

}
