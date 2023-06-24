package com.example.everymutsa.web.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.everymutsa.web.post.domain.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
