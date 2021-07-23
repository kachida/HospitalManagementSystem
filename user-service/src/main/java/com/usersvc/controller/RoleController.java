package com.usersvc.controller;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usersvc.aspect.Loggable;
import com.usersvc.dto.RoleDto;
import com.usersvc.service.RoleServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/roles")
public class RoleController {

	private final RoleServiceImpl roleService;
	public RoleController(RoleServiceImpl roleService)
	{
		this.roleService = roleService;
	}

	// fetch all roles
	@GetMapping("/")
	@Loggable
	@ApiOperation(value = "Retrieve All Roles", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public List<RoleDto> getAllRoles() {
		List<RoleDto> roleList = roleService.getAllRoles();
		return roleList;
	}

	// fetch roles by id
	@GetMapping("/{id}")
	@Loggable
	@ApiOperation(value = "Retrieve role details by role id", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token"),
	})
	public RoleDto getRolesById(@PathVariable long id) {
		return roleService.getRoleById(id);

	}

	// create new user
	@PostMapping("/")
	@Loggable
	@ApiOperation(value = "To create a new role", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Successfully the record is created"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public RoleDto saveUser(@RequestBody RoleDto role) {
	   return roleService.addRole(role);
	}

	// update existing user
	@PutMapping("/{id}")
	@Loggable
	@ApiOperation(value = "To update the existing  role", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully the record is updated"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public RoleDto updateRole(@RequestBody RoleDto role, @PathVariable long id) {
		return roleService.updateRole(role, id);
		
	}

	// delete roles
	@DeleteMapping("/{id}")
	@Loggable
	@ApiOperation(value = "To delete the existing  role", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully the record is deleted"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public void deleteRole(@PathVariable long id) {
		roleService.deleteRole(id);
	}

}
