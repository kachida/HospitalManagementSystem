package com.patientsvc.exceptions;

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

@Data
public class ApiError {
	
	private HttpStatus status;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timeStamp;
	private String message;
	private String debugMessage;
	private List<ApiSubError> subErrors;
	
	
	private ApiError()
	{
		timeStamp = LocalDateTime.now();
	}
	
	public ApiError(HttpStatus status)
	{
		this();
		this.status=status;
	}
	
	public ApiError(HttpStatus status, Throwable ex)
	{
		this();
		this.status=status;
		this.message="unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex)
	{
		this();
		this.status= status;
		this.message = message;
		this.debugMessage= ex.getLocalizedMessage();
	}
	
	
	private void addSubError(ApiSubError subError)
	{
		if(subErrors == null )
		{
			subErrors = new ArrayList<>();
		}
		subErrors.add(subError);
	}

	
	
	private void addValidationError(String object, String field, Object rejectedValue, String message)
	{
		addSubError(new ApiValidationError(object, field, rejectedValue, message));
	}
	
	 private void addValidationError(FieldError fieldError) {
	        this.addValidationError(
	                fieldError.getObjectName(),
	                fieldError.getField(),
	                fieldError.getRejectedValue(),
	                fieldError.getDefaultMessage());
	    }
	
	public void addValidationErrors(List<FieldError> fieldErrors)
	{
		for(FieldError fieldError: fieldErrors)
		{
			addValidationError(fieldError);
		}
	}
	
	
	public void addValidationError(ConstraintViolation<?> cv)
	{
		this.addValidationError(cv.getRootBeanClass().getSimpleName(),
				((PathImpl) cv.getPropertyPath()).getLeafNode().asString(),
				cv.getInvalidValue(),
				cv.getMessage()
				);
	}

	public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations )
	{
		for(ConstraintViolation constraintViolation:constraintViolations)
		{
			addValidationError(constraintViolation);
		}
	}


	
	
	

}
