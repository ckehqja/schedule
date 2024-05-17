package com.sparta.schedule.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sparta.schedule.dto.ScheduleRequestDto;
import com.sparta.schedule.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.exception.NoScheduleException;
import com.sparta.schedule.exception.PwMismatchException;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.service.ScheduleService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ScheduleController {

	int a = 0;
	private final ScheduleService scheduleService;
	private final ScheduleRepository scheduleRepository;

	@GetMapping("/add")
	public String addForm(Model model) {
		model.addAttribute("schedule", new ScheduleRequestDto());
		return "/add";
	}

	@PostMapping("/add")
	public String createSchedule(@Valid @ModelAttribute("schedule") ScheduleRequestDto scheduleRequestDto
		, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "/add";
		}
		ScheduleResponseDto schedule = scheduleService.createSchedule(scheduleRequestDto);
		redirectAttributes.addAttribute("id", schedule.getId());
		return "redirect:/list/{id}";
	}

	@GetMapping("/list")
	public String list(Model model) {
		List<ScheduleResponseDto> schedules = scheduleService.getSchedules();
		model.addAttribute("schedules", schedules);
		return "/list";
	}

	@GetMapping("/list/{id}")
	public String detailForm(@PathVariable("id") Long id, Model model) {
		Schedule findSchedule = scheduleRepository.findById(id)
			.orElseThrow(NoScheduleException::new);
		model.addAttribute("schedule", findSchedule);
		return "/detail";
	}

	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Long id, @RequestParam(required = false) String pw
		, Model model) {
		Schedule findSchedule = scheduleRepository.findById(id).orElseThrow(
			() -> new IllegalArgumentException("Schedule not found"));
		if (!pw.equals(findSchedule.getPw())) {
			throw new PwMismatchException(id);
		}
		model.addAttribute("schedule", findSchedule);
		return "/edit";
	}

	@PostMapping("/edit/{id}")
	public String edit(@PathVariable Long id, @Valid @ModelAttribute("schedule") ScheduleRequestDto scheduleRequestDto,
		BindingResult bindingResult, Model model) {
		// model.addAttribute("schedule", scheduleRepository.findById(id).orElseThrow());
		if (bindingResult.hasErrors()) {
			return "/edit";
		}
		scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
		scheduleService.update(id, scheduleRequestDto);

		return "redirect:/list";
	}

	@GetMapping("/delete/{id}")
	@Tag(name = "delete", description = "삭제")
	public String delete(@PathVariable Long id) {
		scheduleRepository.findById(id).orElseThrow(
			NoScheduleException::new);
		scheduleService.delete(id);
		return "redirect:/list";
	}
}
