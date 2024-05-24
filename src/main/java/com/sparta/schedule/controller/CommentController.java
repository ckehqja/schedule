package com.sparta.schedule.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.schedule.CommonResponse;
import com.sparta.schedule.controller.dto.CommentEditRequestDto;
import com.sparta.schedule.controller.dto.CommentRequestDto;
import com.sparta.schedule.controller.dto.CommentResponseDto;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<CommonResponse<CommentResponseDto>> addComment(
		@Valid @RequestBody CommentRequestDto requestDto) {
		CommentResponseDto commentResponseDto = commentService.addComment(requestDto);
		return ResponseEntity.ok().body(CommonResponse.<CommentResponseDto>builder()
			.statusCode(HttpStatus.OK.value())
			.message("댓글 생성 완료")
			.data(commentResponseDto).build());
	}

	@PutMapping
	public ResponseEntity<CommonResponse<CommentResponseDto>> editComment(
		@Valid @RequestBody CommentEditRequestDto requestDto) {
		CommentResponseDto commentResponseDto = commentService.editComment(requestDto);
		Long commentId = requestDto.getCommentId();

		return  ResponseEntity.ok().body(CommonResponse.<CommentResponseDto>builder()
			.statusCode(HttpStatus.OK.value())
			.message("댓글 수정 완료")
			.data(commentResponseDto).build());
	}
}
