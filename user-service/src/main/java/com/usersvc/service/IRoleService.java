package com.usersvc.service;

import java.util.List;
import java.util.Optional;

import com.usersvc.models.Role;

public interface IRoleService {
	
	public List<Role> getAllRoles();
	public Optional<Role> getRoleById(long id);
	public Role addRole(Role role);
	public Optional<Role> updateRole(Role role,long id);
	public void deleteRole(long id);
	

}
