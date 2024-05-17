package com.sparta.schedule.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sparta.schedule.exception.NoScheduleException;
import com.sparta.schedule.exception.NoSearchException;
import com.sparta.schedule.exception.PwMismatchException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class MyExceptionController {

	@ExceptionHandler(PwMismatchException.class)
	public String handlePwMismatchException(RedirectAttributes redirectAttributes
		, PwMismatchException e) {
		redirectAttributes.addAttribute("id", e.getId());
		redirectAttributes.addFlashAttribute("msg",
			e.getStatus().value() + " - " + e.getMessage());
		log.info(e.getMessage());
		return "redirect:/list/{id}";
	}

	@ExceptionHandler(NoScheduleException.class)
	public String handleNoScheduleException(RedirectAttributes redirectAttributes, NoScheduleException e) {
		redirectAttributes.addFlashAttribute("msg",
			e.getStatus().value() + " - " + e.getMessage());
		return "redirect:/list";
	}

	@ExceptionHandler(NoSearchException.class)
	public String handleNoSearchException(RedirectAttributes redirectAttributes, NoScheduleException e) {
		redirectAttributes.addFlashAttribute("msg",
			e.getStatus().value() + " - " + e.getMessage());
		return "redirect:/list";
	}

}
