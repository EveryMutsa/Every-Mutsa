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
public class PostResponse {

	private Long id;
	private String title;
	private String content;
	private String code;
	private String language;
	private List<MultipartFile> images;
	private Integer heart;

	@Builder
	public static PostResponse fromEntity(Post post, List<MultipartFile> images) {
		PostResponse postResponse = new PostResponse();
		postResponse.setId(post.getId());
		postResponse.setTitle(post.getTitle());
		postResponse.setContent(post.getContent());
		postResponse.setCode(post.getCode());
		postResponse.setLanguage(post.getLanguage());
		postResponse.setImages(images);
		postResponse.setHeart(post.getHeart());
		return postResponse;
	}
}
