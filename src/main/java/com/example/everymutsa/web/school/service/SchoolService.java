package com.example.everymutsa.web.school.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.everymutsa.web.school.domain.dto.SchoolMapper;
import com.example.everymutsa.web.school.domain.dto.SchoolRegister;
import com.example.everymutsa.web.school.domain.dto.SchoolResponse;
import com.example.everymutsa.web.school.domain.dto.SchoolUpdate;
import com.example.everymutsa.web.school.domain.entity.School;
import com.example.everymutsa.web.school.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SchoolService {
	private final SchoolRepository schoolRepository;
	private final SchoolMapper schoolMapper;

	public List<SchoolResponse> readAll() {
		return schoolRepository.findAll().stream()
			.map(schoolMapper::toDto)
			.collect(Collectors.toList());
	}

	public SchoolResponse readOne(Long id) {
		return schoolMapper.toDto(schoolRepository.findByIdOrThrow(id));
	}

	// save

	public Long save(SchoolRegister dto) {
		return schoolRepository.save(schoolMapper.toEntity(dto)).getId();
	}

	public void updateStatus(Long id, SchoolUpdate dto) {
		School foundSchool = schoolRepository.findByIdOrThrow(id);
		foundSchool.updateStatus(dto);
	}

	public void updateEndDate(Long id, Instant newDate) {
		School foundSchool = schoolRepository.findByIdOrThrow(id);
		foundSchool.changePeriod(newDate);
	}

	public void remove(Long id) {
		schoolRepository.deleteById(id);
	}
}
