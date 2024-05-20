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

	public Schedule(ScheduleRequestDto request, String[] image) {
		this.title = request.getTitle();
		this.contents = request.getContents();
		this.username = request.getUsername();
		this.pw = request.getPw();

		if (image != null && image.length > 0) {
			this.imageUploadFileName = image[0];
			this.imageStoreFileName = image[1];
		}
	}


	public void update(ScheduleRequestDto request) {
		this.title = request.getTitle();
		this.contents = request.getContents();
		this.username = request.getUsername();
		this.pw = request.getPw();
	}
}
