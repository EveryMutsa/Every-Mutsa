package com.example.everymutsa.web.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.web.member.domain.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	Optional<Member> findByEmail(String email);

	void deleteByEmail(String email);

	default Member findByEmailOrThrow(String email) {
		return findByEmail(email).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}
	default Member findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}

}
