package com.example.everymutsa.web.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.web.user.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	void deleteByEmail(String email);

	default User findByEmailOrThrow(String email) {
		return findByEmail(email).orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
	}

}
