package com.sparta.schedule.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;

	public ScheduleResponseDto createSchedule(ScheduleRequestDto request) {
		System.out.println("request.getTitle() = " + request.getTitle());
		Schedule savedSchedule = scheduleRepository.save(new Schedule(request));
		return new ScheduleResponseDto(savedSchedule);
	}

	public List<ScheduleResponseDto> getSchedules() {
		return scheduleRepository.findAllByOrderByModifiedAtDesc().stream().map(ScheduleResponseDto::new).toList();
	}

	@Transactional
	public Long update(Long id, ScheduleRequestDto scheduleRequestDto) {
		findSchedule(id).update(scheduleRequestDto);
		return id;
	}

	public Long delete(Long id) {
		scheduleRepository.deleteById(id);
		return id;
	}

	public Schedule findSchedule(Long id) {
		return scheduleRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("Schedule not found"));
	}

}
