package com.sparta.schedule.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.sparta.schedule.controller.dto.ScheduleRequestDto;
import com.sparta.schedule.controller.dto.ScheduleResponseDto;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
class ScheduleServiceTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	ScheduleService service;

	@Test
	@Rollback
	void test() {
		ScheduleRequestDto scheduleRequestDto = new ScheduleRequestDto("dd", "dd", "ddd", "ddd");
		service.createSchedule(scheduleRequestDto);
		List<ScheduleResponseDto> schedules = service.getSchedules();
		for (ScheduleResponseDto schedule : schedules) {
			System.out.println("schedule.getTitle() = " + schedule.getTitle());
		}
	}

}