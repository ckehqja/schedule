package com.sparta.schedule.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.sparta.schedule.controller.dto.ScheduleRequestDto;
import com.sparta.schedule.controller.dto.ScheduleResponseDto;
import com.sparta.schedule.entity.Schedule;
import com.sparta.schedule.file.FileStore;
import com.sparta.schedule.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {
	private final ScheduleRepository scheduleRepository;
	private final FileStore fileStore;

	public ScheduleResponseDto createSchedule(ScheduleRequestDto request) throws IOException {
		String[] image = fileStore.storeFile(request.getImageFile());
		Schedule savedSchedule = scheduleRepository.save(new Schedule(request, image));
		return new ScheduleResponseDto(savedSchedule);
	}

	public List<ScheduleResponseDto> getSchedules() {
		return scheduleRepository.findAllByOrderByCreatedAtDesc().stream().map(ScheduleResponseDto::new).toList();
	}

	@Transactional
	public Long update(Long id, ScheduleRequestDto scheduleRequestDto) throws IOException {
		findSchedule(id).get().update(scheduleRequestDto);
		return id;
	}

	public Long delete(Long id) {
		scheduleRepository.deleteById(id);
		return id;
	}

	public Optional<Schedule> findSchedule(Long id) {
		return scheduleRepository.findById(id);
	}

	public boolean imageFileCheck(MultipartFile multipartFile)   {
		if (multipartFile != null) {
			if (false == isImageFile(multipartFile.getContentType())) {
				throw new IllegalArgumentException("그림파일만 업로드 가능!!");
			}
		}
		 return false;
	}

	private boolean isImageFile(String contentType) {
		return contentType.equals("image/jpeg") ||
			contentType.equals("image/png") ||
			contentType.equals("image/jpg") ||
			contentType.equals("image/gif");
	}
}
