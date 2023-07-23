package com.example.everymutsa.web.board.service;

import java.util.Optional;

import com.example.everymutsa.web.board.domain.enums.BoardType;
import com.example.everymutsa.web.school.domain.entity.School;
import com.example.everymutsa.web.school.repository.SchoolRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.everymutsa.web.board.domain.dto.BoardDto;
import com.example.everymutsa.web.board.domain.entity.Board;
import com.example.everymutsa.web.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository repository;
	private final SchoolRepository schoolRepository;

	public BoardDto save(Long schoolId,String boardType, BoardDto dto) {
		Board entity = new Board();

		entity.setName(dto.getName());
		entity.setType(BoardType.valueOf(boardType));
		entity.setExplain(dto.getExplain());

		School school = schoolRepository.findByIdOrThrow(schoolId);
		entity.setSchool(school);

		return BoardDto.fromEntity(repository.save(entity));
	}

	public void init(Long schoolId) {

		Board notice = new Board();
		notice.setType(BoardType.NOTICE);
		notice.setName("공지사항");
		notice.setExplain("공지사항 게시판입니다.");
		School school = schoolRepository.findByIdOrThrow(schoolId);
		notice.setSchool(school);

		Board question = new Board();
		question.setType(BoardType.QUESTION);
		question.setName("질문사항");
		question.setExplain("질문사항 게시판입니다.");
		question.setSchool(school);

		Board free = new Board();
		free.setType(BoardType.COMMUNITY);
		free.setName("자유게시판");
		free.setExplain("자유 게시판입니다.");
		free.setSchool(school);

		repository.save(notice);
		repository.save(question);
		repository.save(free);

	}

	public Page<BoardDto> readAll(Integer pageNo, Integer pageSize,Long schoolId, String boardType) {

		Pageable pageable = PageRequest.of(pageNo,pageSize);
		Page<Board> boardEntityPage;

		if (boardType.equals(BoardType.NOTICE) || boardType.equals(BoardType.QUESTION)){
			School school = schoolRepository.findByIdOrThrow(schoolId);
			boardEntityPage = repository.findAllBySchoolAndType(school,boardType,pageable);

		}else {
			boardEntityPage = repository.findAllByType(boardType);
		}

		Page<BoardDto> boardDtoPage = boardEntityPage.map(BoardDto::fromEntity);
		return boardDtoPage;
	}

//	public BoardDto readOne(Long schoolId, Long id, String boardType) {
//		Board entity = repository.findByIdOrThrow(id);
//
//		School school = schoolRepository.findByIdOrThrow(schoolId);
//		if (entity.getSchool() == school)
//			return BoardDto.fromEntity(repository.findByIdOrThrow(id));
//
//	}

	public BoardDto update(Long id, BoardDto dto) {
		Optional<Board> optionalBoard = repository.findById(id);
		if (optionalBoard.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		Board entity = optionalBoard.get();
		entity.setName(dto.getName());
		entity.setExplain(dto.getExplain());
		return BoardDto.fromEntity(repository.save(entity));
	}

	public void remove(Long id) {
		if (!repository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
	}

}
