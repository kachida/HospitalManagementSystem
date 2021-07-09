package com.usersvc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usersvc.aspect.Loggable;
import com.usersvc.models.Role;
import com.usersvc.models.User;
import com.usersvc.service.IRoleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/rolesvc")
public class RoleController {

	@Autowired
	IRoleService roleService;

	// fetch all roles
	@GetMapping("/roles")
	@Loggable
	@ApiOperation(value = "Retrieve All Roles", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public ResponseEntity<List<Role>> getAllRoles() {
		List<Role> roleList = roleService.getAllRoles();
		return new ResponseEntity<List<Role>>(roleList, new HttpHeaders(), HttpStatus.OK);
	}

	// fetch roles by id
	@GetMapping("/roles/{id}")
	@Loggable
	@ApiOperation(value = "Retrieve role details by role id", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved the record"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token"),
	})
	public ResponseEntity<Role> getRolesById(@PathVariable long id) {
		Optional<Role> roleDetails = roleService.getRoleById(id);
		if (roleDetails.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(roleDetails.get());
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(roleDetails.get());
		}
	}

	// create new user
	@PostMapping("/roles")
	@Loggable
	@ApiOperation(value = "To create a new role", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 201, message = "Successfully the record is created"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public ResponseEntity<Role> saveUser(@RequestBody Role role) {
		return new ResponseEntity<Role>(roleService.addRole(role), HttpStatus.CREATED);
	}

	// update existing user
	@PutMapping("/roles/{id}")
	@Loggable
	@ApiOperation(value = "To update the existing  role", produces = "application/json")
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully the record is updated"),
	        @ApiResponse(code = 204, message = "No resource found for this id"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden, check jwt token")
	})
	public ResponseEntity<Role> updateRole(@RequestBody Role role, @PathVariable long id) {
		Optional<Role> roleDetails = roleService.updateRole(role, id);
		if (roleDetails.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(role);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(roleDetails.get());
		}
	}

	// delete roles
	@DeleteMapping("/roles/{id}")
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
