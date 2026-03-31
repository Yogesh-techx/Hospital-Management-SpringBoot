package com.hospital.management.exception;

import com.hospital.management.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	// Resource Not Found 404
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
		
		ApiResponse<Object> response = new ApiResponse<>();
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setMessage(ex.getMessage());
		response.setData(null);
		
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	// Duplicate Resource 409
	@ExceptionHandler(DuplicateResourceException.class)
	public ResponseEntity<ApiResponse<Object>> handleDuplicateResource(DuplicateResourceException ex) {
		
		ApiResponse<Object> response = new ApiResponse<>();
		response.setStatus(HttpStatus.CONFLICT.value());
		response.setMessage(ex.getMessage());
		response.setData(null);
		
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	// Invalid Operation 400
	@ExceptionHandler(InvalidOperationException.class)
	public ResponseEntity<ApiResponse<Object>> handleInvalidOperation(InvalidOperationException ex) {
		
		ApiResponse<Object> response = new ApiResponse<>();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMessage(ex.getMessage());
		response.setData(null);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	// Validation Errors (@Valid) 400
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiResponse<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
		
		String errorMessage = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.joining(", "));
		
		ApiResponse<Object> response = new ApiResponse<>();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMessage(errorMessage);
		response.setData(null);
		
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}
	
	// Generic Exception 500
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
		
		ApiResponse<Object> response = new ApiResponse<>();
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setMessage("Something went wrong: " + ex.getMessage());
		response.setData(null);
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}