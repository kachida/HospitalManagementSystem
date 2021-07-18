package com.usersvc.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.core.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.usersvc.aspect.Loggable;
import com.usersvc.models.Role;
import com.usersvc.repository.IRoleRepository;

@Service
public class RoleServiceImpl implements IRoleService{
	
	
	private final IRoleRepository roleRepository;
	
	public RoleServiceImpl(IRoleRepository roleRepository)
	{
		this.roleRepository = roleRepository;
	}
	
	
	//Get All roles
		@Transactional(readOnly = true)
		@Loggable
		
		public List<Role> getAllRoles()
		{
			return roleRepository.findAll();
		}
	
	//Get role by id
	@Loggable
	@Transactional(readOnly = true)
	@Cacheable(value = "role", key = "#id")
	public Optional<Role> getRoleById(long id)
	{
		Optional<Role> roleDetails =  roleRepository.findById(id);
		return roleDetails;
	}
	
	//Add new role
	
	@Loggable
	@Transactional(propagation = Propagation.REQUIRED)
	@CachePut(value = "role", key = "#role.id")
	public Role addRole(Role role)
	{
		return roleRepository.save(role);
		
	}
	
	//update existing role
	@Loggable
	@CachePut(value = "role", key = "#id")
	@Transactional(propagation = Propagation.REQUIRES_NEW,
					rollbackFor = Exception.class,
					noRollbackFor = EntityNotFoundException.class)
	public Optional<Role> updateRole(Role updatedRole,long id)
	{
		Optional<Role> roleDetails = roleRepository.findById(id);
		 if(roleDetails.isPresent())
		 {
			Role role= roleDetails.get();
			role.setId(updatedRole.getId());
			role.setRole_name(updatedRole.getRole_name());
			roleRepository.save(role);
		
		}
		 
		 return roleDetails;
		
				
		
	}
	
	//delete role
	@Loggable
	@CacheEvict(value = "role", key="#id")
	@Transactional(propagation = Propagation.REQUIRES_NEW,
			rollbackFor = Exception.class,
			noRollbackFor = EntityNotFoundException.class)
	public void deleteRole(long id)
	{
		roleRepository.deleteById(id);
	}

	

}
