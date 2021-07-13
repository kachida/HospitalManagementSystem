package com.vitalsignsvc.exceptions;

import javax.persistence.EntityNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		String error = "Malformed Json Request";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex)
	{
		
		return null;
		
	}
	
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex)
	{
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation Error");
		apiError.addValidationErrors(ex.getConstraintViolations());
		return buildResponseEntity(apiError);
		
	}
	
	@ExceptionHandler(java.util.NoSuchElementException.class)
	protected ResponseEntity<Object> handleNoSuchElementException(java.util.NoSuchElementException ex)
	{
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage("No resource found for this id");
		return buildResponseEntity(apiError);
	}

	@ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class)
	protected ResponseEntity<Object> handleNoSuchElementException(org.springframework.dao.EmptyResultDataAccessException ex)
	{
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage("No resource found for this id");
		return buildResponseEntity(apiError);
	}

	
}
