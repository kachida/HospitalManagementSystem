package com.usersvc.service;

import java.util.List;
import com.usersvc.dto.RoleDto;

public interface IRoleService {
	
	public List<RoleDto> getAllRoles();
	public RoleDto getRoleById(long id);
	public RoleDto addRole(RoleDto role);
	public RoleDto updateRole(RoleDto role,long id);
	public void deleteRole(long id);
	

}
