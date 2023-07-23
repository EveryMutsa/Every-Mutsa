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
	private String code;
	private String language;
	private List<MultipartFile> images;
	private Integer heart;

	@Builder
	public PostRegister(String title, String content, String code, String language, List<MultipartFile> images, Integer heart) {
		this.title = title;
		this.content = content;
		this.code = code;
		this.language = language;
		this.images = images;
		this.heart = heart;
	}

	public Post toDto(String images) {
		return new Post(this.getTitle(), this.getContent(), this.getCode(), this.getLanguage(), images, this.getHeart());
	}
}
