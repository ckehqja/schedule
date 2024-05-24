package com.sparta.schedule.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiMissMathException extends RuntimeException {
	private HttpStatus status;

	public ApiMissMathException(String message) {
		super(message);
		this.status = HttpStatus.NOT_FOUND;
	}
}
