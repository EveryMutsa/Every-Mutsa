package com.example.everymutsa.web.comment.domain.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class CommentRequest {
	private Long  parentId;
	private String content;
	private int depth;
	private boolean deleted;

	public CommentRequest(Long parentId, String content, boolean deleted) {
		this.parentId = parentId;
		this.content = content;
		this.deleted = false;
		this.depth = 0;
	}
}
