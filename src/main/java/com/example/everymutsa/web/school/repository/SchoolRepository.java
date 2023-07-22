package com.example.everymutsa.web.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.web.school.domain.entity.School;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

	default School findByIdOrThrow(Long id) {
		return findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}
}
