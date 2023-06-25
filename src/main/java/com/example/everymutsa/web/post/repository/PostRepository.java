package com.example.everymutsa.web.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.everymutsa.web.post.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
}
