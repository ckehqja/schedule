package com.sparta.schedule.controller.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.sparta.schedule.entity.Schedule;

import lombok.Getter;

@Getter
public class ScheduleResponseDto {
	private Long id;
	private String title;
	private String contents;
	private String username;
	private String pw;
	private String imageUploadFileName;
	private String imageStoreFileName;
	private String attachUploadFileName;
	private String attachStoreFileName;

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
		this.imageUploadFileName = schedule.getImageUploadFileName();
		this.imageStoreFileName = schedule.getImageStoreFileName();
		this.attachUploadFileName = schedule.getAttachUploadFileName();
		this.attachStoreFileName = schedule.getAttachStoreFileName();
	}
}