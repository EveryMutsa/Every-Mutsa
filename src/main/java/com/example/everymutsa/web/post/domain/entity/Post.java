package com.example.everymutsa.web.post.domain.entity;

import java.util.ArrayList;
import java.util.List;

import com.example.everymutsa.common.BaseEntity;
import com.example.everymutsa.web.board.domain.entity.BoardEntity;
import com.example.everymutsa.web.comment.domain.entity.Comment;
import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.post.domain.dto.PostParam;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private BoardEntity board;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "post")
	private List<Comment> comments = new ArrayList<>();
}