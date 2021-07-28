package com.usersvc.exceptions;

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


// TODO: Auto-generated Javadoc
/**
 * The Class RestExceptionHandler.
 *
 * @author : Kannappan
 * @version : 1.0
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	/**
	 * Handle http message not readable.
	 *
	 * @param ex the ex
	 * @param headers the headers
	 * @param status the status
	 * @param request the request
	 * @return the response entity
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		String error = "Malformed Json Request";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}
	
	/**
	 * Builds the response entity.
	 *
	 * @param apiError the api error
	 * @return the response entity
	 */
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

	/**
	 * Handle entity not found.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex)
	{
		
		return null;
		
	}
	
	/**
	 * Handle constraint violation.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex)
	{
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation Error");
		apiError.addValidationErrors(ex.getConstraintViolations());
		return buildResponseEntity(apiError);
		
	}
	
	/**
	 * Handle no such element exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(java.util.NoSuchElementException.class)
	protected ResponseEntity<Object> handleNoSuchElementException(java.util.NoSuchElementException ex)
	{
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage("No resource found for this id");
		return buildResponseEntity(apiError);
	}

	/**
	 * Handle no such element exception.
	 *
	 * @param ex the ex
	 * @return the response entity
	 */
	@ExceptionHandler(org.springframework.dao.EmptyResultDataAccessException.class)
	protected ResponseEntity<Object> handleNoSuchElementException(org.springframework.dao.EmptyResultDataAccessException ex)
	{
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage("No resource found for this id");
		return buildResponseEntity(apiError);
	}

	
}
