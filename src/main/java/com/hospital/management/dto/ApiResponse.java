package com.hospital.management.dto;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {

	private int status;
	private String message;
	private T data;
	
	public ApiResponse() {}

	public ApiResponse(HttpStatus status, T data) {
		this.status = status.value();
		this.message = status.getReasonPhrase();
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	
}