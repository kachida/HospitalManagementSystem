package com.usersvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.usersvc.aspect.Loggable;
import com.usersvc.models.User;
import com.usersvc.repository.IUserRepository;


@Service
public class UserServiceImpl implements IUserService{
	

	
	@Autowired
	private IUserRepository userRepository;
	
	@PersistenceContext
	private EntityManager entityManager;
	

	//Get all users
	@Override
	@Transactional(readOnly = true)
	@Loggable
	public List<User> getAllUsers(int pageNo, int pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<User> pagedResult = userRepository.findAll(paging);
		
		if(pagedResult.hasContent())
		{
			return pagedResult.getContent();
		}else
		{
			return new ArrayList<User>();
		}
		

	}
	
	
	//Get all users - filter with name
	@Override
	@Transactional(readOnly = true)
	@Loggable
	public List<User> getUsersWithNameFilter(String query,int pageNo, int pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize);
		Page<User> pagedResult = userRepository.findAllByUsernameContains(query, paging);
		
		if(pagedResult.hasContent())
		{
			return pagedResult.getContent();
		}else
		{
			return new ArrayList<User>();
		}
		

	}
	
	
	//Get user by id
	@Transactional(readOnly = true)
	@Loggable
	public Optional<User> getUserById(long id)
	{
		Optional<User> userDetails =  userRepository.findById(id);
		return userDetails;
	}
	
	//Add new user
	@Transactional(propagation = Propagation.REQUIRED)
	@Loggable
	public User addUser(User user)
	{
		return userRepository.save(user);
		
	}
	
	//update existing user
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW,
					rollbackFor = Exception.class,
					noRollbackFor = EntityNotFoundException.class)
	public Optional<User> updateUser(User updatedUser,long id)
	{
		Optional<User> userDetails = userRepository.findById(id);
		 if(userDetails.isPresent())
		 {
			User user= userDetails.get();
			user.setRole(updatedUser.getRole());
			user.setUsername(updatedUser.getUsername());
			user.setEmail(updatedUser.getEmail());
			user.setPhonenumber(updatedUser.getPhonenumber());
			user.setAddress(updatedUser.getAddress());
			user.setId(id);
			 userRepository.save(user);
		}
		 
		 return userDetails;
		
				
		
	}
	
	//delete user
	@Loggable
	@Transactional(propagation = Propagation.REQUIRES_NEW,
			rollbackFor = Exception.class,
			noRollbackFor = EntityNotFoundException.class)
	public void deleterUser(long id)
	{
		userRepository.deleteById(id);
	}


	@Override
	@Loggable
	@Transactional(readOnly = true)
	public List<User> getUsersWithMultipleFilter(String username, String role, String email, int pageNo, int pageSize,
			String sortBy) {
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

		List<User> userList = entityManager.createQuery(query).setFirstResult(pageNo).setMaxResults(pageSize).getResultList();
		return userList;
	}


	//Query with Named Queries
	@Override
	@Loggable
	@Transactional(readOnly = true)
	public List<User> getUsersWithEmailIdFilter(String query, int pageNo, int pageSize, String sortBy) {
		// TODO Auto-generated method stub
		Query q = entityManager.createNamedQuery("User.FindByEmail");
		q.setParameter(1, query);
		List<User> userList=q.getResultList();
		return userList;
	}

	//Query with Named Queries
	@Override
	@Loggable
	@Transactional(readOnly = true)
	public List<User> getUsersWithRoleAndUserNameFilter(String username, String role, int pageNo, int pageSize,
			String sortBy) {
		// TODO Auto-generated method stub
		Query q = entityManager.createNamedQuery("User.FindByRoleAndUsername");
		q.setParameter(1, role);
		q.setParameter(2, username);
		List<User> userList=q.getResultList();
		return userList;
	}

}
