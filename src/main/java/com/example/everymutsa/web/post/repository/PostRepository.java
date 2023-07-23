package com.example.everymutsa.web.post.repository;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.everymutsa.web.board.domain.entity.BoardEntity;
import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.post.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {

	Page<Post> findAllByBoard(BoardEntity board, Pageable pageable);

	Page<Post> findAllByMember(Member member, Pageable pageable);
}
