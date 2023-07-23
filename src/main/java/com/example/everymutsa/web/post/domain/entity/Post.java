package com.example.everymutsa.web.post.domain.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.everymutsa.common.BaseEntity;
import com.example.everymutsa.web.board.domain.entity.BoardEntity;
import com.example.everymutsa.web.comment.domain.entity.Comment;
import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.post.domain.dto.PostUpdate;

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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
	private String sourceCode;

	@Column(length = 32)
	private String language;

	@Column(columnDefinition = "TEXT")
	private String images;

	private Integer likeCount;

	@Builder
	public Post(String title, String content, String sourceCode, String language, String images, Integer likeCount) {
		this.title = title;
		this.content = content;
		this.sourceCode = sourceCode;
		this.language = language;
		this.images = images;
		this.likeCount = likeCount;
	}

	public void update(PostUpdate postUpdate, String images) {

		this.title = postUpdate.getTitle();
		this.content = postUpdate.getContent();
		this.sourceCode = postUpdate.getSourceCode();
		this.language = postUpdate.getLanguage();
		this.images = images;
		this.likeCount = postUpdate.getLikeCount();
	}

	public List<String> getImageNames() {
		return Arrays.stream(images.split(",")).toList();
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