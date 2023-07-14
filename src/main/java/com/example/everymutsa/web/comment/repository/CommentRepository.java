package com.example.everymutsa.web.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.web.comment.domain.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	default Comment findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
	}
}
