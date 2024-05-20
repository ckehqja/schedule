package com.sparta.schedule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@SpringBootTest
class ScheduleServiceTest {

	@PersistenceContext
	EntityManager entityManager;

	@Autowired
	ScheduleService service;

}