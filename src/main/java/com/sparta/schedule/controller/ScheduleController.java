package com.sparta.schedule.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import com.sparta.schedule.controller.dto.ScheduleRequestDto;
import com.sparta.schedule.controller.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.exception.NoScheduleException;
import com.sparta.schedule.exception.PwMismatchException;
import com.sparta.schedule.file.FileStore;
import com.sparta.schedule.repository.ScheduleRepository;
import com.sparta.schedule.service.ScheduleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "ScheduleController", description = "ScheduleController")
@Controller
@RequiredArgsConstructor
public class ScheduleController {

	private final ScheduleService scheduleService;
	private final ScheduleRepository scheduleRepository;
	private final FileStore fileStore;

	@Operation(summary = "추가폼으로", description = "추가폼으로 이동")
	@Parameter(name = "model", description = "뷰에 반환")
	@GetMapping("/add")
	public String addForm(@ModelAttribute("schedule") ScheduleRequestDto scheduleRequestDto) {
		return "/add";
	}

	@Operation(summary = "스케쥴 생성", description = "생성")
	@Parameter(name = "ScheduleRequestDto, bindingResult, redirectAttributes"
		, description = "dto를 받아서 스케쥴 생성하고, 오류는 bindingResult, redirectAttributes")
	@PostMapping("/add")
	public String createSchedule(@Valid @ModelAttribute("schedule") ScheduleRequestDto requestDto
		, BindingResult bindingResult, RedirectAttributes redirectAttributes  ) throws IOException {
		try {
			scheduleService.imageFileCheck(requestDto.getAttachFile());
			scheduleService.imageFileCheck(requestDto.getImageFile());


		} catch (IllegalArgumentException e) {
			log.info(e.getMessage());
		}
		if (bindingResult.hasErrors()) {
			return "/add";
		}
		ScheduleResponseDto schedule = scheduleService.createSchedule(requestDto);
		redirectAttributes.addFlashAttribute("schedule", schedule);
		redirectAttributes.addAttribute("id", schedule.getId());
		return "redirect:/list/{id}";
	}

	@Operation(summary = "목록", description = "목록 이동")
	@Parameter(name = "model", description = "뷰에 반환")
	@GetMapping("/list")
	public String list(Model model) {
		List<ScheduleResponseDto> schedules = scheduleService.getSchedules();
		model.addAttribute("schedules", schedules);
		return "/list";
	}

	@Operation(summary = "상세", description = "상세페이지 이동")
	@Parameter(name = "model, id", description = "id로 스케줄을 찾아 model로 뷰에 반환")
	@GetMapping("/list/{id}")
	public String detailForm(@PathVariable("id") Long id, Model model) {
		Schedule findSchedule = scheduleService.findSchedule(id)
			.orElseThrow(NoScheduleException::new);
		model.addAttribute("schedule", findSchedule);
		return "/detail";
	}

	@Operation(summary = "수정폼", description = "수정폼으로 이동")
	@Parameter(name = "id, pw, model", description = "pw가 일치하면 이동")
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable("id") Long id, @RequestParam(required = false) String pw, Model model) {
		Schedule findSchedule = scheduleService.findSchedule(id).orElseThrow(
			() -> new IllegalArgumentException("Schedule not found"));
		if (!pw.equals(findSchedule.getPw())) {
			throw new PwMismatchException(id);
		}
		model.addAttribute("schedule", findSchedule);
		return "/edit";
	}

	@Operation(summary = "수정", description = "수정")
	@Parameter(name = "id, pw, model", description = "pw가 일치하면 이동")
	@PostMapping("/edit/{id}")
	public String edit(@PathVariable Long id, @Valid @ModelAttribute("schedule") ScheduleRequestDto scheduleRequestDto,
		BindingResult bindingResult) throws IOException {
		if (bindingResult.hasErrors()) {
			return "/edit";
		}
		scheduleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
		scheduleService.update(id, scheduleRequestDto);

		return "redirect:/list";
	}

	@Operation(summary = "삭제", description = "삭제")
	@Parameter(name = "id", description = "id로 찾아 삭제")
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		scheduleRepository.findById(id).orElseThrow(
			NoScheduleException::new);
		scheduleService.delete(id);
		return "redirect:/list";
	}

	@ResponseBody
	@GetMapping("/images/{filename}")
	public Resource downloadImage(@PathVariable String filename) throws
		MalformedURLException {
		return new UrlResource("file:" + fileStore.getFullPath(filename));
	}

	@GetMapping("/attach/{itemId}")
	public ResponseEntity<Resource> downloadAttach(@PathVariable Long itemId)
		throws MalformedURLException {
		Schedule findSchedule = scheduleService.findSchedule(itemId)
			.orElseThrow(NoScheduleException::new);
		String storeFileName = findSchedule.getAttachStoreFileName();
		String uploadFileName = findSchedule.getAttachUploadFileName();
		UrlResource resource = new UrlResource(
			"file:" + fileStore.getFullPath(storeFileName));

		log.info("uploadFileName={}", uploadFileName);

		String encodedUploadFileName = UriUtils.encode(
			uploadFileName, StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\""
			+ encodedUploadFileName + "\"";

		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
			.body(resource);
	}
}
