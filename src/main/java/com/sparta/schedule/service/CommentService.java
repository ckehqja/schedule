package com.sparta.schedule.service;

import org.springframework.stereotype.Repository;

import com.sparta.schedule.controller.dto.CommentRequestDto;
import com.sparta.schedule.controller.dto.CommentResponseDto;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final ScheduleRepository scheduleRepository;

	public CommentResponseDto addComment(CommentRequestDto requestDto) {
		Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId()).orElseThrow(
			() -> new NullPointerException("댓글)일정이 없습니다.!!!"));

		Comment comment = commentRepository.save(new Comment(requestDto, schedule));
		return new CommentResponseDto(comment);
	}
}
