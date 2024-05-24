package com.sparta.schedule.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiNoMissMathException extends RuntimeException {
	private HttpStatus status;

	public ApiNoMissMathException(String message) {
		super(message);
		this.status = HttpStatus.NOT_FOUND;
	}
}
