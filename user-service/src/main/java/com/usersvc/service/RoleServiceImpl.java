package com.usersvc.service;


import java.lang.reflect.Type;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.usersvc.aspect.Loggable;
import com.usersvc.dto.RoleDto;
import com.usersvc.models.Role;
import com.usersvc.repository.IRoleRepository;


// TODO: Auto-generated Javadoc
/**
 * The Class RoleServiceImpl.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Service
public class RoleServiceImpl implements IRoleService{
	
	
	/** The role repository. */
	private final IRoleRepository roleRepository;
	
	/** The model mapper. */
	private final ModelMapper modelMapper; 
	
	/**
	 * Instantiates a new role service impl.
	 *
	 * @param roleRepository the role repository
	 * @param modelMapper the model mapper
	 */
	public RoleServiceImpl(IRoleRepository roleRepository,ModelMapper modelMapper)
	{
		this.roleRepository = roleRepository;
		this.modelMapper = modelMapper;
	}
	
	
	/**
	 * Gets the all roles.
	 *
	 * @return the all roles
	 */
	//Get All roles
		@Transactional(readOnly = true)
		@Loggable
		
		public List<RoleDto> getAllRoles()
		{
			List<Role> rolesList =  roleRepository.findAll();
			Type listType = new TypeToken<List<RoleDto>>() {}.getType();
			List<RoleDto> rolesDtoList =  modelMapper.map(rolesList, listType);
			return rolesDtoList;
		}
	
	/**
	 * Gets the role by id.
	 *
	 * @param id the id
	 * @return the role by id
	 */
	//Get role by id
	@Loggable
	@Transactional(readOnly = true)
	@Cacheable(value = "role", key = "#id")
	public RoleDto getRoleById(long id)
	{
		Role role =  roleRepository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException());
		RoleDto roleDetails = modelMapper.map(role, RoleDto.class);
		return roleDetails;
	}
	
	//Add new role
	
	/**
	 * Adds the role.
	 *
	 * @param roleDto the role dto
	 * @return the role dto
	 */
	@Loggable
	@Transactional(propagation = Propagation.REQUIRED)
	@CachePut(value = "role", key = "#role.id")
	public RoleDto addRole(RoleDto roleDto)
	{
		Role newRole = modelMapper.map(roleDto, Role.class);
		Role role =  roleRepository.save(newRole);
		RoleDto roleDetails = modelMapper.map(role, RoleDto.class);
		return roleDetails;
		
	}
	
	/**
	 * Update role.
	 *
	 * @param updatedRoleDto the updated role dto
	 * @param id the id
	 * @return the role dto
	 */
	//update existing role
	@Loggable
	@CachePut(value = "role", key = "#id")
	@Transactional(propagation = Propagation.REQUIRES_NEW,
					rollbackFor = Exception.class,
					noRollbackFor = EntityNotFoundException.class)
	public RoleDto updateRole(RoleDto updatedRoleDto,long id)
	{
		    Role updatedRole = modelMapper.map(updatedRoleDto, Role.class);
			Role role = roleRepository.findById(id).orElseThrow(() -> new java.util.NoSuchElementException());;
			role.setId(updatedRole.getId());
			role.setRole_name(updatedRole.getRole_name());
			Role roleEntity = roleRepository.save(role);
			RoleDto roleDetails = modelMapper.map(roleEntity, RoleDto.class);
			return roleDetails;
		
	}
	
	/**
	 * Delete role.
	 *
	 * @param id the id
	 */
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
