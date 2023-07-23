package com.example.everymutsa.web.comment.domain.dto;

import java.time.Instant;
import java.util.List;

import com.example.everymutsa.web.comment.domain.entity.Comment;

import lombok.Builder;
import lombok.Data;

@Data
public class CommentResponse {
	private Long id;
	private String content;
	//TODO : 사용자 프로필 출력
	private String writer;
	private Instant createdAt;
	private Instant updatedAt;
	private int depth;
	private List<CommentResponse> replies;

	@Builder
	public static CommentResponse fromEntity(Comment comment) {
		CommentResponse response = new CommentResponse();
		response.id = comment.getId();
		response.content = comment.getContent();
		response.writer  = comment.getDeleted() ? "" : comment.getMember().getNickName();
		response.createdAt = comment.getCreatedAt();
		response.updatedAt = comment.getUpdatedAt();
		response.depth = comment.getDepth();
		return response;
	}
}
