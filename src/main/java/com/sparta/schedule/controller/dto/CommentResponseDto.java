package com.sparta.schedule.controller.dto;

import java.time.LocalDateTime;

import com.sparta.schedule.entity.Comment;

import lombok.Getter;

@Getter
public class CommentResponseDto {
	private Long id;
	private String contents;
	private Long scheduleId;
	private Long userId;
	private LocalDateTime createdAt;

	public CommentResponseDto(Comment comment) {
		this.id = comment.getId();
		this.scheduleId = comment.getSchedule().getId();
		this.contents = comment.getContents();
		this.createdAt = comment.getCreatedAt();
		this.userId = comment.getUserId();
	}
}
