package com.example.everymutsa.web.post.domain.entity;

import com.example.everymutsa.web.post.domain.dto.PostParam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(length = 128)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(columnDefinition = "TEXT")
	private String code;

	@Column(length = 32)
	private String language;

	@Column(length = 128)
	private String image;

	private Integer heart;

	public static Post createPost(PostParam postParam) {
		Post post = new Post();
		post.setTitle(postParam.getTitle());
		post.setContent(postParam.getContent());
		post.setCode(postParam.getCode());
		post.setLanguage(postParam.getLanguage());
		post.setImage(postParam.getImage());
		post.setHeart(postParam.getHeart());
		return post;
	}

	public static Post updateByParam(Post post, PostParam postParam) {

		post.setTitle(postParam.getTitle());
		post.setContent(postParam.getContent());
		post.setCode(postParam.getCode());
		post.setLanguage(postParam.getLanguage());
		post.setImage(postParam.getImage());
		post.setHeart(postParam.getHeart());
		return post;
	}

	/*
	created_at, updated_at : BaseEntity 로 상속받을 예정
	 */

	// @ManyToOne
	// private Board board;

	// @ManyToOne
	// private User user;

	// @OneToMany(mappedBy = "comment")
	// private List<Comment> comments = new ArrayList<>();
}