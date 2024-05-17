package com.sparta.schedule.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NoScheduleException extends RuntimeException {
	private HttpStatus status;

	public NoScheduleException() {
		super("이미 사라진 일정입니다.");
		this.status = HttpStatus.NOT_FOUND;
	}
}
