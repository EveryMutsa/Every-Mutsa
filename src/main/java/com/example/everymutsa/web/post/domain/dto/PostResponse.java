package com.example.everymutsa.web.post.domain.dto;

import java.io.File;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.everymutsa.web.post.domain.entity.Post;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponse {

	private Long id;
	private String title;
	private String content;
	private String sourceCode;
	private String language;
	private List<File> images;
	private List<String> preImages;
	private Integer likeCount;

	@Builder
	public static PostResponse fromEntity(Post post) {
		PostResponse postResponse = new PostResponse();
		postResponse.setId(post.getId());
		postResponse.setTitle(post.getTitle());
		postResponse.setContent(post.getContent());
		postResponse.setSourceCode(post.getSourceCode());
		postResponse.setLanguage(post.getLanguage());
		postResponse.setPreImages(post.getImageNames());
		postResponse.setLikeCount(post.getLikeCount());
		return postResponse;
	}
}
