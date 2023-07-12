package com.example.everymutsa.web.comment.domain.dto;

import java.time.Instant;

import com.example.everymutsa.web.comment.domain.entity.Comment;

import lombok.Builder;
import lombok.Data;

@Data
public class CommentResponse {
	private String content;
	private Instant createdAt;
	private Instant updatedAt;

	@Builder
	public CommentResponse(Comment comment) {
		this.content = comment.getContent();
		this.createdAt = comment.getCreatedAt();
		this.updatedAt = comment.getUpdatedAt();
	}
}
