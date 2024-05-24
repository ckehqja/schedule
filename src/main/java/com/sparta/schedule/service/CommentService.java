package com.sparta.schedule.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.controller.dto.CommentEditRequestDto;
import com.sparta.schedule.controller.dto.CommentRequestDto;
import com.sparta.schedule.controller.dto.CommentResponseDto;
import com.sparta.schedule.entity.Comment;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.exception.ApiNoMissMathException;
import com.sparta.schedule.exception.ApiNoSearchException;
import com.sparta.schedule.repository.CommentRepository;
import com.sparta.schedule.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final ScheduleRepository scheduleRepository;

	public CommentResponseDto addComment(CommentRequestDto requestDto) {
		Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId()).orElseThrow(
			() -> new ApiNoSearchException("댓글)일정이 없습니다.!!!"));

		Comment comment = commentRepository.save(new Comment(requestDto, schedule));
		return new CommentResponseDto(comment);
	}

	@Transactional
	public CommentResponseDto editComment(CommentEditRequestDto requestDto) {
		Schedule schedule = scheduleRepository.findById(requestDto.getScheduleId())
			.orElseThrow(() -> new ApiNoSearchException("댓글)일정이 없습니다.!!!"));

		Comment comment = commentRepository.findById(requestDto.getCommentId())
			.orElseThrow(() -> new ApiNoSearchException("댓글이 없습니다.!!!"));

		if (requestDto.getUserId() != comment.getUserId())
			throw new ApiNoMissMathException("일정과 유저 아이디가 일치하지 않습니다.");
		if (comment.getSchedule() != schedule)
			throw new ApiNoMissMathException("일정과 메모가 일치하지 않습니다.!!!");

		comment.editContents(requestDto);
		return new CommentResponseDto(comment);
	}
}
