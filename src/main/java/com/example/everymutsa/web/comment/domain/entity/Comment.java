package com.example.everymutsa.web.comment.domain.entity;

import java.time.Instant;

import com.example.everymutsa.common.BaseEntity;
import com.example.everymutsa.web.comment.domain.dto.CommentRequest;
import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.post.domain.entity.Post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(columnDefinition = "TEXT")
	private String content;

	private Integer depth;
	private Boolean deleted;

	@Builder
	public static Comment fromDto(CommentRequest request) {
		Comment comment = new Comment();
		comment.content = request.getContent();
		comment.deleted = request.isDeleted();
		comment.depth = request.getDepth();
		return comment;
	}
	public void update(CommentRequest request) {
		this.content = request.getContent();
	}

	public void delete() {
		this.content = "삭제된 댓글입니다.";
		this.deleted = true;
	}


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id")
	private Post post;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id") // 셀프조인을 위한 조인 컬럼
	private Comment parentComment;

	public void setPost(Post post) {
		this.post = post;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public void setParentComment(Comment parentComment) {
		this.parentComment = parentComment;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}
}
