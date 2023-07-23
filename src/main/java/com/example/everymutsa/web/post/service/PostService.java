package com.example.everymutsa.web.post.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.everymutsa.api.file.dto.FileResponse;
import com.example.everymutsa.api.file.service.FileService;
import com.example.everymutsa.web.board.domain.entity.Board;
import com.example.everymutsa.web.board.repository.BoardRepository;
import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.member.repository.MemberRepository;
import com.example.everymutsa.web.post.domain.dto.PostRegister;
import com.example.everymutsa.web.post.domain.dto.PostResponse;
import com.example.everymutsa.web.post.domain.dto.PostUpdate;
import com.example.everymutsa.web.post.domain.entity.Post;
import com.example.everymutsa.web.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
	private final FileService fileService;

	@Transactional
	public PostResponse save(PostRegister postRegister) {
		Post save = postRepository.save(postRegister.toDto(stringFromFiles(postRegister.getImages())));
		return PostResponse.fromEntity(save);
	}

	@Transactional
	public void update(Long id, PostUpdate postUpdate) {
		Post findPost = postRepository.findByIdOrThrow(id);
		findPost.update(postUpdate, stringFromFiles(postUpdate.getImages()));
	}

	@Transactional
	public void remove(Long id) {
		postRepository.deleteById(id);
	}

	public PostResponse readOne(Long id) throws IOException {
		Post post = postRepository.findByIdOrThrow(id);
		PostResponse postResponse = PostResponse.fromEntity(post);
		postResponse.setImages(filesFromString(post.getImageNames()));
		return postResponse;
	}

	public Page<PostResponse> readAllByMemberId(Long id, Pageable pageable) throws IOException {
		Member member = memberRepository.findByIdOrThrow(id);
		Page<Post> posts = postRepository.findAllByMember(member, pageable);
		Page<PostResponse> postResponsePage = posts.map(PostResponse::fromEntity);
		for (PostResponse postResponse : postResponsePage) {
			postResponse.setImages(filesFromString(postResponse.getPreImages()));
		}
		return postResponsePage;
	}

	public Page<PostResponse> readAllByBoardId(Long id, Pageable pageable) throws IOException {
		Board board = boardRepository.findByIdOrThrow(id);
		Page<Post> posts = postRepository.findAllByBoard(board, pageable);
		Page<PostResponse> postResponsePage = posts.map(PostResponse::fromEntity);
		for (PostResponse postResponse : postResponsePage) {
			postResponse.setImages(filesFromString(postResponse.getPreImages()));
		}
		return postResponsePage;
	}


	public String stringFromFiles(List<MultipartFile> list) {
		StringBuilder images = new StringBuilder();
		for (MultipartFile image : list) {
			FileResponse store = fileService.store(image);
			images.append(store.getFileName());
		}
		return images.toString();
	}

	public List<File> filesFromString(List<String> imageNames) throws IOException {
		List<File> list = new ArrayList<>();
		for (String imageName : imageNames) {
			Resource resource = fileService.load(imageName);
			list.add(resource.getFile());
		}
		return list;
	}
}
