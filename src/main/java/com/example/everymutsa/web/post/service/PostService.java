package com.example.everymutsa.web.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.everymutsa.web.post.domain.dto.PostParam;
import com.example.everymutsa.web.post.domain.entity.Post;
import com.example.everymutsa.web.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;

	@Transactional
	public Long save(PostParam postParam) {
		Post post = Post.createPost(postParam);
		postRepository.save(post);

		return post.getId();
	}

	@Transactional
	public PostParam update(Long id, PostParam postParam) {
		Post findPost = postRepository.findById(id).get();
		findPost = Post.updateByParam(findPost, postParam);
		return PostParam.createFromPost(findPost);
	}

	@Transactional
	public void remove(Long id) {
		postRepository.deleteById(id);
	}

	public List<Post> findPosts() {
		return postRepository.findAll();
	}

	public Post findOne(Long id) {
		return postRepository.findById(id).get();
	}

}
