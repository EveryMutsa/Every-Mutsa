package com.example.everymutsa.web.post.domain.dto;

import com.example.everymutsa.web.post.domain.entity.Post;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

	public static PostParam createFromPost(Post post) {
		PostParam npp = new PostParam();
		npp.title = post.getTitle();
		npp.content = post.getContent();
		npp.code = post.getCode();
		npp.language = post.getLanguage();
		npp.image = post.getImage();
		npp.heart = post.getHeart();
		return npp;
	}
}
