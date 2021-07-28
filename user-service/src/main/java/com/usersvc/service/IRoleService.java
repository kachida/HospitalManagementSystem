package com.usersvc.service;

import java.util.List;
import com.usersvc.dto.RoleDto;

// TODO: Auto-generated Javadoc
/**
 * The Interface IRoleService.
 *
 * @author : Kannappan
 * @version : 1.0
 */
public interface IRoleService {
	
	/**
	 * Gets the all roles.
	 *
	 * @return the all roles
	 */
	public List<RoleDto> getAllRoles();
	
	/**
	 * Gets the role by id.
	 *
	 * @param id the id
	 * @return the role by id
	 */
	public RoleDto getRoleById(long id);
	
	/**
	 * Adds the role.
	 *
	 * @param role the role
	 * @return the role dto
	 */
	public RoleDto addRole(RoleDto role);
	
	/**
	 * Update role.
	 *
	 * @param role the role
	 * @param id the id
	 * @return the role dto
	 */
	public RoleDto updateRole(RoleDto role,long id);
	
	/**
	 * Delete role.
	 *
	 * @param id the id
	 */
	public void deleteRole(long id);
	

}
