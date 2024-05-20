package com.sparta.schedule.entity;

import com.sparta.schedule.controller.dto.ScheduleRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor
public class Schedule extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private String contents;
	private String username;
	private String pw;
	private String imageUploadFileName;
	private String imageStoreFileName;
	private String attachUploadFileName;
	private String attachStoreFileName;

	public Schedule(ScheduleRequestDto request, String[] attach, String[] image) {
		this.title = request.getTitle();
		this.contents = request.getContents();
		this.username = request.getUsername();
		this.pw = request.getPw();

		if (attach != null && attach.length > 0) {
			this.imageUploadFileName = attach[0];
			this.imageStoreFileName = attach[1];
		}

		if (image != null && image.length > 0) {
			this.attachUploadFileName = image[0];
			this.attachStoreFileName = image[1];
		}
	}

	public void update(ScheduleRequestDto request) {
		this.title = request.getTitle();
		this.contents = request.getContents();
		this.username = request.getUsername();
		this.pw = request.getPw();
	}
}
