package com.example.everymutsa.web.comment.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.web.comment.domain.dto.CommentResponse;
import com.example.everymutsa.web.comment.domain.dto.CommentUpdateRequest;
import com.example.everymutsa.web.comment.repository.CommentRepository;

import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@Slf4j
class CommentServiceTest {

	@Autowired
	EntityManager em;
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	CommentService commentService;

//	@Test
//	public void 댓글_등록_조회() throws Exception {
//	    // given
//		Long save = commentService.save("새로운 댓글");
//		// when
//		CommentResponse commentResponse = commentService.findById(save);
//		// then
//		Assertions.assertEquals("수정한 댓글", commentResponse.getContent());
//	}
//
//	@Test
//	public void 댓글_수정() throws Exception {
//	    // given
//		Long save = commentService.save("새로운 댓글");
//	    // when
//		CommentResponse commentResponse = commentService.update(save, new CommentUpdateRequest("수정한 댓글"));
//		// then
//		Assertions.assertEquals("수정한 댓글", commentResponse.getContent());
//	}
//
//	@Test
//	public void 댓글_삭제() throws Exception {
//	    // given
//		Long save = commentService.save("새로운 댓글");
//	    // when
//		commentService.remove(save);
//	    // then
//		assertThrows(EntityNotFoundException.class,() -> commentService.findById(save));
//	}
}