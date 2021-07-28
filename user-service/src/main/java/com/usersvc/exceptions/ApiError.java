package com.usersvc.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.hibernate.validator.internal.engine.path.PathImpl;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.validation.ConstraintViolation;


// TODO: Auto-generated Javadoc
/**
 * ApiError
 *
 * @author : Kannappan
 * @version : 1.0
 * @return the java.lang. string
 */

@Data
public class ApiError {
	
	/** The status. */
	private HttpStatus status;
	
	/** The time stamp. */
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timeStamp;
	
	/** The message. */
	private String message;
	
	/** The debug message. */
	private String debugMessage;
	
	/** The sub errors. */
	private List<ApiSubError> subErrors;
	
	
	/**
	 * Instantiates a new api error.
	 */
	private ApiError()
	{
		timeStamp = LocalDateTime.now();
	}
	
	/**
	 * Instantiates a new api error.
	 *
	 * @param status the status
	 */
	public ApiError(HttpStatus status)
	{
		this();
		this.status=status;
	}
	
	/**
	 * Instantiates a new api error.
	 *
	 * @param status the status
	 * @param ex the ex
	 */
	public ApiError(HttpStatus status, Throwable ex)
	{
		this();
		this.status=status;
		this.message="unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}
	
	/**
	 * Instantiates a new api error.
	 *
	 * @param status the status
	 * @param message the message
	 * @param ex the ex
	 */
	public ApiError(HttpStatus status, String message, Throwable ex)
	{
		this();
		this.status= status;
		this.message = message;
		this.debugMessage= ex.getLocalizedMessage();
	}
	
	
	/**
	 * Adds the sub error.
	 *
	 * @param subError the sub error
	 */
	private void addSubError(ApiSubError subError)
	{
		if(subErrors == null )
		{
			subErrors = new ArrayList<>();
		}
		subErrors.add(subError);
	}

	
	
	/**
	 * Adds the validation error.
	 *
	 * @param object the object
	 * @param field the field
	 * @param rejectedValue the rejected value
	 * @param message the message
	 */
	private void addValidationError(String object, String field, Object rejectedValue, String message)
	{
		addSubError(new ApiValidationError(object, field, rejectedValue, message));
	}
	
	 /**
 	 * Adds the validation error.
 	 *
 	 * @param fieldError the field error
 	 */
 	private void addValidationError(FieldError fieldError) {
	        this.addValidationError(
	                fieldError.getObjectName(),
	                fieldError.getField(),
	                fieldError.getRejectedValue(),
	                fieldError.getDefaultMessage());
	    }
	
	/**
	 * Adds the validation errors.
	 *
	 * @param fieldErrors the field errors
	 */
	public void addValidationErrors(List<FieldError> fieldErrors)
	{
		for(FieldError fieldError: fieldErrors)
		{
			addValidationError(fieldError);
		}
	}
	
	
	/**
	 * Adds the validation error.
	 *
	 * @param cv the cv
	 */
	public void addValidationError(ConstraintViolation<?> cv)
	{
		this.addValidationError(cv.getRootBeanClass().getSimpleName(),
				((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
				cv.getInvalidValue(),
				cv.getMessage()
				);
	}

	/**
	 * Adds the validation errors.
	 *
	 * @param constraintViolations the constraint violations
	 */
	@SuppressWarnings({"rawtypes"})
	public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations )
	{
		for(ConstraintViolation constraintViolation:constraintViolations)
		{
			addValidationError(constraintViolation);
		}
	}


	
	
	

}
