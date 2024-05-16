package com.sparta.schedule.dto;

import java.time.LocalDateTime;

import com.sparta.schedule.entity.Schedule;

import lombok.Getter;

@Getter
public class ScheduleResponseDto {
	private Long id;
	private String title;
	private String contents;
	private String username;
	private String pw;

	private LocalDateTime createdAt;
	private LocalDateTime modifiedAt;

	public ScheduleResponseDto(Schedule schedule) {
		this.id = schedule.getId();
		this.title = schedule.getTitle();
		this.contents = schedule.getContents();
		this.username = schedule.getUsername();
		this.pw = schedule.getPw();
		this.createdAt = schedule.getCreatedAt();
		this.modifiedAt = schedule.getModifiedAt();
	}
}
