package com.example.everymutsa.web.school.controller;

import java.net.URI;
import java.time.Instant;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.everymutsa.web.school.domain.dto.SchoolRegister;
import com.example.everymutsa.web.school.domain.dto.SchoolResponse;
import com.example.everymutsa.web.school.domain.dto.SchoolUpdate;
import com.example.everymutsa.web.school.service.SchoolService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
public class SchoolController {

	private final SchoolService schoolService;

	@GetMapping("/{id}")
	public ResponseEntity<SchoolResponse> readById(@PathVariable Long id) {
		return ResponseEntity.ok(schoolService.readOne(id));
	}

	@GetMapping
	public ResponseEntity<List<SchoolResponse>> readAll() {
		return ResponseEntity.ok(schoolService.readAll());
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody SchoolRegister dto) {
		return ResponseEntity
			.created(URI.create("/schools/" + schoolService.save(dto)))
			.build();
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/{id}")
	public ResponseEntity<Void> changeEndDate(
		@PathVariable Long id,
		@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Instant endDate
	) {
		schoolService.updateEndDate(id, endDate);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@PatchMapping("/{id}")
	public ResponseEntity<Void> renewStatus(
		@PathVariable Long id,
		@RequestBody SchoolUpdate dto
	) {
		schoolService.updateStatus(id, dto);
		return ResponseEntity.ok().build();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(
		@PathVariable Long id
	) {
		schoolService.remove(id);
		return ResponseEntity.ok("성공적으로 삭제되었습니다.");
	}
}
