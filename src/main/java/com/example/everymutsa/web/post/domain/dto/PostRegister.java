package com.example.everymutsa.web.post.domain.dto;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.everymutsa.web.post.domain.entity.Post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostRegister {

	private String title;
	private String content;
	private String sourceCode;
	private String language;
	private List<MultipartFile> images;
	private Integer likeCount;

	@Builder
	public PostRegister(String title, String content, String sourceCode, String language, List<MultipartFile> images, Integer likeCount) {
		this.title = title;
		this.content = content;
		this.sourceCode = sourceCode;
		this.language = language;
		this.images = images;
		this.likeCount = likeCount;
	}

	public Post toDto(String images) {
		return new Post(this.getTitle(), this.getContent(), this.getSourceCode(), this.getLanguage(), images, this.getLikeCount());
	}
}
