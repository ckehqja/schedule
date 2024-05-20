package com.sparta.schedule.controller.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDto {
	@NotBlank
	@Size(min = 1, max = 200)
	private String title;
	private String contents;
	private String username;
	@NotBlank
	private String pw;
	private MultipartFile attachFile;
	private MultipartFile imageFile;
}
