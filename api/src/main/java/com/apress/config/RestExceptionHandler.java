package com.apress.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.apress.dto.ErrorResponse;
import com.apress.exception.ResourceNotFoundException;

//@ControllerAdvice
public class RestExceptionHandler {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
		ErrorResponse error = new ErrorResponse();
		error.setCode(HttpStatus.NOT_FOUND.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> exceptionToDoHandler(Exception ex) {
		LOG.error("Checked exception ocurred", ex);
		ErrorResponse error = new ErrorResponse();
		error.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		error.setMessage("An error ocurred, check api logs");
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}