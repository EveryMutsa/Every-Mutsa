package com.example.everymutsa.web.post.repository;
import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.everymutsa.web.board.domain.entity.BoardEntity;
import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.post.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
	default Post findByIdOrThrow(Long id) {
		return findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND));
	}
	Page<Post> findAllByBoard(BoardEntity board, Pageable pageable);

	Page<Post> findAllByMember(Member member, Pageable pageable);
}
