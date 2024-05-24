package com.sparta.schedule.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentEditRequestDto {
	@NotBlank
	private String contents;
	@NotNull
	private Long userId;
	@NotNull
	private Long commentId;
	@NotNull
	private Long scheduleId;
}
