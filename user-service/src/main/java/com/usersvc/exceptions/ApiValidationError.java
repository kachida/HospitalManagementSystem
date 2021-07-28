package com.usersvc.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


// TODO: Auto-generated Javadoc

/**
 * The Class ApiValidationError.
 *
 * @author : Kannappan
 * @version : 1.0
 */

@Data
@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public class ApiValidationError extends ApiSubError {

	/** The object. */
	private String object;
	
	/** The field. */
	private String field;
	
	/** The rejected value. */
	private Object rejectedValue;
	
	/** The message. */
	private String message;
	
	/**
	 * Instantiates a new api validation error.
	 *
	 * @param object the object
	 * @param msg the msg
	 */
	ApiValidationError(String object, String msg)
	{
		this.message=msg;
		this.object=object;
	}


	
}


