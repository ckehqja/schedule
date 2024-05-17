package com.sparta.schedule.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class NoSearchException extends RuntimeException {
	private HttpStatus status;

	public NoSearchException() {
		super(" 이미 삭제되어 조회 불가능 !! ");
		this.status = HttpStatus.NOT_FOUND;
	}
}
