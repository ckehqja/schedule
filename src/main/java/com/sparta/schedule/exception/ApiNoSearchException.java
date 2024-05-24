package com.sparta.schedule.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiNoSearchException extends RuntimeException {
	private HttpStatus status;

	public ApiNoSearchException(String message) {
		super(message);
		this.status = HttpStatus.NOT_FOUND;
	}
}
