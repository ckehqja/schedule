package com.sparta.schedule.entity;

import com.sparta.schedule.dto.ScheduleRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// @Max(200)
	// @NotBlank
	@Column(name = "title")
	private String title;
	@Column(name = "contents")
	private String contents;
	@Column(name = "username")
	private String username;
	// @NotBlank
	@Column(name = "pw")
	private String pw;

	public Schedule(ScheduleRequestDto request) {
		this.title = request.getTitle();
		this.contents = request.getContents();
		this.username = request.getUsername();
		this.pw = request.getPw();
	}

	public void update(ScheduleRequestDto request) {
		this.title = request.getTitle();
		this.contents = request.getContents();
		this.username = request.getUsername();
		this.pw = request.getPw();
	}
}
