package com.example.everymutsa.web.comment.repository;

import java.util.List;
import java.util.Optional;

import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.post.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.web.comment.domain.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	default Comment findByIdOrThrow(Long id) {
		return findById(id).orElseThrow(() ->   new EntityNotFoundException(ErrorCode.COMMENT_NOT_FOUND));
	}

	@Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.depth = 0")
	Page<Comment> findByPost(Long postId, Pageable pageable);

	@Query("SELECT c FROM Comment c WHERE c.member.id = :memberId AND c.depth = 0")
	Page<Comment> findByMember(Long memberId, Pageable pageable);
	List<Comment> findByParentCommentId(Long commentId);


}
