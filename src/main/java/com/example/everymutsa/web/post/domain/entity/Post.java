package com.example.everymutsa.web.post.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
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

	@Id @GeneratedValue
	@Column(name = "id")
	private Long id;

	private String title;

	@Lob
	private String content;

	@Lob
	private String code;

	private String language;

	private String image;

	private int heart;

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
