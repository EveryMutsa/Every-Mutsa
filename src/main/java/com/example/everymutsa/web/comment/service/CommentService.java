package com.example.everymutsa.web.comment.service;

import com.example.everymutsa.common.exception.InvalidValueException;
import com.example.everymutsa.web.member.repository.MemberRepository;
import com.example.everymutsa.web.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.web.comment.domain.dto.CommentResponse;
import com.example.everymutsa.web.comment.domain.dto.CommentRequest;
import com.example.everymutsa.web.comment.domain.entity.Comment;
import com.example.everymutsa.web.comment.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

	private final CommentRepository commentRepository;
	private final PostRepository postRepository;
	private final MemberRepository memberRepository;

	@Transactional
	public CommentResponse save(Long pid, CommentRequest request, String email) {
		Comment saveComment = Comment.fromDto(request);
		saveComment.setPost(postRepository.findByIdOrThrow(pid));
		saveComment.setMember(memberRepository.findByEmailOrThrow(email));
		if(request.getParentId() != null){
			Comment parent = commentRepository.findByIdOrThrow(request.getParentId());
			saveComment.setDepth(parent.getDepth()+1);
			saveComment.setParentComment(parent);
		}

		return CommentResponse.fromEntity(commentRepository.save(saveComment));
	}

	public CommentResponse getOne(Long id, Long pid){
		Comment comment = commentRepository.findByIdOrThrow(id);
		isRightPid(pid, comment);
		return CommentResponse.fromEntity(comment);
	}
	public Page<CommentResponse> readAllByPostId(Long pid, int pageNo, int pageSize){
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<CommentResponse> comments = commentRepository.findByPost(pid, pageable).map(CommentResponse :: fromEntity);
		for (CommentResponse comment : comments.getContent()) {
			getAllReplies(comment);
		}
		return comments;
	}

	public Page<CommentResponse> readAllByMemberId(Long mid, int pageNo, int pageSize){
		Pageable pageable = PageRequest.of(pageNo, pageSize);
		Page<CommentResponse> comments = commentRepository.findByMember(mid, pageable).map(CommentResponse :: fromEntity);

		for (CommentResponse comment : comments.getContent()) {
			getAllReplies(comment);
		}

		return comments;
	}

	private void getAllReplies(CommentResponse response) {
		List<CommentResponse> replies = getReplies(response.getId());
		response.setReplies(replies);
		for (CommentResponse reply : replies) {
			getAllReplies(reply); // 재귀 호출
		}
	}

	private List<CommentResponse> getReplies(Long commentId) {
		return commentRepository.findByParentCommentId(commentId).stream().map(CommentResponse::fromEntity).toList();
	}

	@Transactional
	public CommentResponse update(Long id, Long pid, CommentRequest request, String email) {
		Comment comment = commentRepository.findByIdOrThrow(id);
		isRightPid(pid, comment);
		isRightUser(email, comment);
		comment.update(request);
		return CommentResponse.fromEntity(comment);
	}

	@Transactional
	public void delete(Long id, Long pid, String email) {
		Comment comment = commentRepository.findByIdOrThrow(id);
		isRightPid(pid, comment);
		isRightUser(email, comment);
		comment.delete();
	}

	private void isRightPid(Long pid, Comment comment){
		if(!comment.getPost().getId().equals(pid)) throw new InvalidValueException(ErrorCode.COMMENT_NOT_MATCHED_POST);
	}

	private void isRightUser(String email, Comment comment){
		if(! comment.getMember().equals(memberRepository.findByEmailOrThrow(email))) throw new InvalidValueException(ErrorCode.COMMENT_NOT_YOURS);
	}
}