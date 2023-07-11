package com.example.everymutsa.web.post.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.everymutsa.web.post.domain.dto.PostParam;
import com.example.everymutsa.web.post.domain.entity.Post;
import com.example.everymutsa.web.post.service.PostService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

	private final PostService postService;
	@GetMapping
	public Result readPostAll() {

		List<Post> findPosts = postService.findPosts();
		List<PostParam> collect = findPosts.stream()
			.map(m -> PostParam.createFromPost(m))
			.collect(Collectors.toList());

		return new Result(collect);
	}

	@GetMapping("/{id}")
	public PostParam readPostOne(@PathVariable Long id) {
		try	{
			Post findPost = postService.findOne(id);
			return PostParam.createFromPost(findPost);
		} catch (RuntimeException e) {
			log.error("RuntimeException occurred : {} ", e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@Data
	@AllArgsConstructor
	static class Result<T> {
		private T data;
	}

	@PostMapping
	public PostParam createPost(@RequestBody PostParam postParam) {
		Long saveId = postService.save(postParam);
		return PostParam.createFromPost(postService.findOne(saveId));
	}

	@PatchMapping("/{id}")
	public PostParam updatePost(@PathVariable Long id, @RequestBody PostParam postParam) {
		log.info("id = {}", id);
		PostParam updateParam = postService.update(id, postParam);
		return updateParam;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePost(@PathVariable Long id) {
		try {
			postService.remove(id);
		} catch(RuntimeException e) {
			log.error("RuntimeException occurred : {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
		return ResponseEntity.ok("Deletion successful");
	}
}
