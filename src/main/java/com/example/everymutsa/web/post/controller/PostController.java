package com.example.everymutsa.web.post.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.everymutsa.api.file.service.FileService;
import com.example.everymutsa.web.post.domain.dto.PostRegister;
import com.example.everymutsa.web.post.domain.dto.PostResponse;
import com.example.everymutsa.web.post.domain.dto.PostUpdate;
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

	@GetMapping("{id}")
	public ResponseEntity<PostResponse> readOneById(
		@PathVariable("id") Long id
	) {
		try {
			PostResponse postResponse = postService.readOne(id);
			return ResponseEntity.ok(postResponse);

		} catch (IOException e) {
			log.error("readOne {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<PostResponse> createPost(@RequestBody PostRegister postRegister) {
		PostResponse save = postService.save(postRegister);
		return ResponseEntity.ok(save);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<Void> updatePost(@PathVariable Long id, @RequestBody PostUpdate postUpdate) {
		log.info("id = {}", id);
		postService.update(id, postUpdate);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletePost(@PathVariable Long id) {
		postService.remove(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("members/{memberId}")
	public Page<PostResponse> readAllByMemberId(
		@PathVariable("memberId") Long memberId,
		@RequestParam(name = "no", defaultValue = "0") Integer pageNo,
		@RequestParam(name = "size", defaultValue = "20") Integer pageSize
	) {
		try {
			Page<PostResponse> postResponsePage =
				postService.readAllByMemberId(memberId, PageRequest.of(pageNo, pageSize));
			return postResponsePage;
		} catch (IOException e) {
			log.error("pageByMember error {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@GetMapping("boards/{boardId}")
	public Page<PostResponse> readAllByboardId(
		@PathVariable("boardId") Long boardId,
		@RequestParam(name = "no", defaultValue = "0") Integer pageNo,
		@RequestParam(name = "size", defaultValue = "20") Integer pageSize
	) {
		try {
			Page<PostResponse> postResponsePage =
				postService.readAllByBoardId(boardId, PageRequest.of(pageNo, pageSize));
			return postResponsePage;
		} catch (IOException e) {
			log.error("pageByBoard error {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
