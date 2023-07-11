package com.example.everymutsa.web.post.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.example.everymutsa.web.post.domain.dto.PostParam;
import com.example.everymutsa.web.post.domain.entity.Post;
import com.example.everymutsa.web.post.repository.PostRepository;

import jakarta.persistence.EntityManager;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class PostServiceTest {

	@Autowired
	EntityManager em;
	@Autowired
	PostService postService;
	@Autowired
	PostRepository postRepository;

	@Test
	public void 게시글_생성_조회() throws Exception {
		// given
		PostParam postParam = new PostParam("오늘의 글", "내용", "코드", "언어", "이미지 명", 4);
		PostParam postParam2 = new PostParam("내일의 글", "내용2", "코드2", "언어2", "이미지 명2", 2);
		// when
		Long savePost = postService.save(postParam);
		Long savePost2 = postService.save(postParam2);

		// then
		assertEquals("오늘의 글",postService.findOne(savePost).getTitle());
		assertEquals("코드",postService.findOne(savePost).getCode());

		assertEquals("내용2",postService.findOne(savePost2).getContent());
		assertEquals(2,postService.findOne(savePost2).getHeart());
	}

	@Test
	public void 게시글_다수_조회() throws Exception {

		// given
		PostParam postParam = new PostParam("오늘의 글", "내용", "코드", "언어", "이미지 명", 4);
		PostParam postParam2 = new PostParam("내일의 글", "내용2", "코드2", "언어2", "이미지 명2", 2);

		// when
		Long savePost = postService.save(postParam);
		Long savePost2 = postService.save(postParam2);

		List<Post> posts = postService.findPosts();

		// then
		for (Post post : posts) {
			System.out.println(post.getTitle());
		}

	}

	@Test
	public void 게시글_수정() throws Exception {
		// given
		PostParam postParam = new PostParam("오늘의 글", "내용", "코드", "언어", "이미지 명", 4);

		// when
		Long savePost = postService.save(postParam);
		Post post = postService.findOne(savePost);
		post.setTitle("새로운 이름");

		// then

		Post rePost = postService.findOne(savePost);

		assertEquals("새로운 이름",rePost.getTitle());
		assertEquals(false,rePost.getTitle().equals("오늘의 글"));

	}

	@Test
	public void 게시글_삭제() throws Exception {
		// given
		PostParam postParam = new PostParam("오늘의 글", "내용", "코드", "언어", "이미지 명", 4);
		PostParam postParam2 = new PostParam("내일의 글", "내용2", "코드2", "언어2", "이미지 명2", 2);

		Long savePost = postService.save(postParam);
		Long savePost2 = postService.save(postParam2);

		assertEquals("오늘의 글",postService.findOne(savePost).getTitle());
		assertEquals("코드2",postService.findOne(savePost2).getCode());
		// when

		postService.remove(savePost);
		postService.remove(savePost2);

		// then
		assertThrows(NoSuchElementException.class,() -> postService.findOne(savePost));
		assertThrows(NoSuchElementException.class,() -> postService.findOne(savePost2));
	}
}