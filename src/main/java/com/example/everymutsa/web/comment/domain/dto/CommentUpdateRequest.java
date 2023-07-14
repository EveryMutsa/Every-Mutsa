package com.example.everymutsa.web.comment.domain.dto;

import lombok.Data;

@Data
public class CommentUpdateRequest {
	private String content;

	public CommentUpdateRequest(String content) {
		this.content = content;
	}
}
