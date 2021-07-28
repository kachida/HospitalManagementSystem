package com.usersvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



// TODO: Auto-generated Javadoc
/**
 * RoleDto
 *
 * @author Kannappan
 * @Version 1.0
 */
@Data

/**
 * Instantiates a new role dto.
 *
 * @param id the id
 * @param role_name the role name
 */
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

	/** The id. */
	long id;
	
	/** The role name. */
	private String role_name;
	
}
