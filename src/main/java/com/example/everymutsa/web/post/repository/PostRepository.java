package com.example.everymutsa.web.post.repository;

import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.everymutsa.web.post.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post,Long> {
    default Post findBYIdOrThrow(Long id) {
        return findById(id).orElseThrow(() -> new EntityNotFoundException(ErrorCode.POST_NOT_FOUND));
    }
}
