package com.example.everymutsa.web.log.repository;

import java.time.Instant;
import java.util.List;

import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.web.log.domain.entity.Log;
import com.example.everymutsa.web.school.domain.entity.School;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
	default Log findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.LOG_NOT_FOUND));
	}
	Page<Log> findAllBySchool(School school, Pageable pageable);
	List<Log> findByCreatedAtBetweenAndSchool(Instant startOfDay, Instant endOfDay, School school);
}
