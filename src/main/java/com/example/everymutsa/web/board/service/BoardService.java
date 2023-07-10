package com.example.everymutsa.web.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.everymutsa.web.board.domain.dto.BoardDto;
import com.example.everymutsa.web.board.domain.entity.BoardEntity;
import com.example.everymutsa.web.board.repository.BoardRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {
	private final BoardRepository repository;

	public BoardDto createBoard(BoardDto dto) {
		BoardEntity entity = new BoardEntity();
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setExplain(dto.getExplain());
		entity.setType(dto.getType());

		return BoardDto.fromEntity(repository.save(entity));
	}

	public List<BoardDto> readBoardAll() {
		List<BoardDto> boardList = new ArrayList<>();

		for (BoardEntity entity : repository.findAll()) {
			boardList.add(BoardDto.fromEntity(entity));
		}
		return boardList;
	}

	public BoardDto readBoard(Long id) {
		Optional<BoardEntity> optionalBoard = repository.findById(id);
		if (optionalBoard.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}

		return BoardDto.fromEntity(optionalBoard.get());

	}

	public BoardDto updateBoard(Long id, BoardDto dto) {
		Optional<BoardEntity> optionalBoard = repository.findById(id);
		if (optionalBoard.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		BoardEntity entity = optionalBoard.get();
		entity.setName(dto.getName());
		entity.setExplain(dto.getExplain());
		return BoardDto.fromEntity(repository.save(entity));
	}

	public void deleteBoard(Long id) {
		if (!repository.existsById(id)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		repository.deleteById(id);
	}

}
