package com.example.everymutsa.web.post.domain.dto;

import lombok.Data;

@Data
public class PostParam {

	String title;
	String content;
	String code;
	String language;
	String image;
	Integer heart;

	public PostParam(String title, String content, String code, String language, String image, Integer heart) {
		this.title = title;
		this.content = content;
		this.code = code;
		this.language = language;
		this.image = image;
		this.heart = heart;
	}
}
