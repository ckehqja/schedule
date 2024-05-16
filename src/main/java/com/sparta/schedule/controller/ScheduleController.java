package com.sparta.schedule.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

	private final ScheduleService scheduleService;
	private final ScheduleRepository scheduleRepository;

	@PostMapping("/add")
	public String createSchedule(@ModelAttribute("schedule") ScheduleRequestDto scheduleRequestDto,
		RedirectAttributes redirectAttributes) {
		ScheduleResponseDto schedule = scheduleService.createSchedule(scheduleRequestDto);
		redirectAttributes.addAttribute("id", schedule.getId());
		return "redirect:/list/{id}";
	}

	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("schedule", new ScheduleRequestDto());
		return "/add";
	}

	@GetMapping("/list")
	public String list(Model model) {
		List<ScheduleResponseDto> schedules = scheduleService.getSchedules();
		model.addAttribute("schedules", schedules);
		return "/list";
	}

	@GetMapping("/list/{id}")
	public String detailForm(@PathVariable("id") Long id, Model model) {
		Schedule findSchedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
		model.addAttribute("schedule", findSchedule);
		return "/detail";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Long id, Model model) {
		Schedule findSchedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
		model.addAttribute("schedule", findSchedule);
		return "/edit";
	}

	@PostMapping("/edit/{id}")
	public String edit(@PathVariable Long id, @ModelAttribute("schedule") ScheduleRequestDto scheduleRequestDto) {
		Schedule schedule = scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
		scheduleService.update(id, scheduleRequestDto);

		return "redirect:/list";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		System.out.println("Delete Schedule");
		scheduleService.delete(id);
		return "redirect:/list";
	}
}
