package com.sparta.schedule.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class PwMismatchException extends RuntimeException {
	private HttpStatus status;
	private Long id;

	public PwMismatchException(Long id) {
		super("비밀번호 불일치!!!");
		this.status = HttpStatus.UNAUTHORIZED;
		this.id = id;
	}
}
