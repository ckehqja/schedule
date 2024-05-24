package com.sparta.schedule.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sparta.schedule.CommonResponse;
import com.sparta.schedule.exception.ApiNoMissMathException;
import com.sparta.schedule.exception.ApiNoSearchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "ApiExceptionController")
@RestControllerAdvice
public class ApiExceptionController {
	//모든 에러 -> 하위 에러에서 못받을 때
	@ExceptionHandler(Exception.class)
	public ResponseEntity handleException(Exception e) {
		// NestedExceptionUtils.getMostSpecificCause() -> 가장 구체적인 원인, 즉 가장 근본 원인을 찾아서 반환
		log.error("[Exception] cause: {} , message: {}", NestedExceptionUtils.getMostSpecificCause(e), e.getMessage());
		return ResponseEntity.status(BAD_REQUEST).body(CommonResponse.builder()
			.statusCode(BAD_REQUEST.value())
			.data(e.getMessage()).build());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<CommonResponse> handleMethodArgumentNotValidException(
		MethodArgumentNotValidException e) {
		ConcurrentHashMap<Object, Object> validationMessage = new ConcurrentHashMap<>();
		for (FieldError fieldError : e.getFieldErrors()) {
			validationMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
		}
		log.info(e.getMessage());
		return ResponseEntity.status(BAD_REQUEST).body(CommonResponse.builder()
			.statusCode(BAD_REQUEST.value())
			.message(e.getMessage())
			.data(validationMessage).build());
	}

	@ExceptionHandler(ApiNoSearchException.class)
	public ResponseEntity<CommonResponse> apiNoSearchException(ApiNoSearchException e) {
		log.info(e.getMessage());
		return ResponseEntity.status(BAD_REQUEST).body(CommonResponse.builder()
			.statusCode(BAD_REQUEST.value())
			.message(e.getMessage())
			.data(e.getMessage()).build());
	}

	@ExceptionHandler(ApiNoMissMathException.class)
	public ResponseEntity<CommonResponse> apiNoMissMathException(ApiNoSearchException e) {
		log.info(e.getMessage());
		return ResponseEntity.status(BAD_REQUEST).body(CommonResponse.builder()
			.statusCode(BAD_REQUEST.value())
			.message(e.getMessage())
			.data(e.getMessage()).build());
	}
}
