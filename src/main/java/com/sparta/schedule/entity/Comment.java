package com.sparta.schedule.entity;

import com.sparta.schedule.controller.dto.CommentEditRequestDto;
import com.sparta.schedule.controller.dto.CommentRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table
@NoArgsConstructor
public class Comment extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String contents;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schedule_id")
	private Schedule schedule;
	private Long userId;

	public Comment(CommentRequestDto requestDto, Schedule schedule) {
		this.contents = requestDto.getContents();
		this.userId = requestDto.getUserId();
		this.schedule = schedule;
	}

	public void editContents(CommentEditRequestDto requestDto) {
		this.contents = requestDto.getContents();
	}
}
