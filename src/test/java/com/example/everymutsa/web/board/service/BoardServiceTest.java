package com.example.everymutsa.web.board.service;

import static org.assertj.core.api.Assertions.*;

import com.example.everymutsa.web.board.domain.enums.BoardType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.everymutsa.web.board.domain.dto.BoardDto;
import com.example.everymutsa.web.board.domain.entity.BoardEntity;
import com.example.everymutsa.web.board.repository.BoardRepository;

import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BoardServiceTest {

	@Autowired
	EntityManager entityManager;

	@Autowired
	BoardRepository boardRepository;

	@Autowired
	BoardService boardService;

	@Test
	public void createBoardTest() {
		// Given
		BoardDto dto = new BoardDto();
		dto.setId(1L);
		dto.setName("Board Name");
		dto.setExplain("Board Explanation");
		dto.setType(BoardType.COMMUNITY);

		// When
		boardService.createBoard(dto);

		// Then
		BoardEntity savedEntity = entityManager.find(BoardEntity.class, 1L);
		assertThat(savedEntity).isNotNull();
		assertThat(savedEntity.getId()).isEqualTo(1);
		assertThat(savedEntity.getName()).isEqualTo("Board Name");
		assertThat(savedEntity.getExplain()).isEqualTo("Board Explanation");
		assertThat(savedEntity.getType()).isEqualTo(BoardType.COMMUNITY);
	}

	@Test
	public void readBoardTest() {
		// given
		BoardDto dto = new BoardDto();
		dto.setId(1L);
		dto.setName("Board Name");
		dto.setExplain("Board Explanation");
		dto.setType(BoardType.NOTICE);

		// when
		boardService.createBoard(dto);
		BoardDto result = boardService.readBoard(1L);

		// then
		assertThat(result).isEqualTo(dto);
	}

	@Test
	public void ReadBoardAllTest(){
		// given
		BoardDto dto = new BoardDto();
		dto.setId(1L);
		dto.setName("Board Name");
		dto.setExplain("Board Explanation");
		dto.setType(BoardType.NOTICE);

		BoardDto dto2 = new BoardDto();
		dto2.setId(2L);
		dto2.setName("Board Name2");
		dto2.setExplain("Board Explanation2");
		dto2.setType(BoardType.QUESTION);
		boardService.createBoard(dto);

		boardService.createBoard(dto2);

		List<BoardDto> boardList = new ArrayList<>();
		boardList.add(dto);
		boardList.add(dto2);

		//when
		boardService.readBoardAll();

		//then
		for (BoardDto boardDto : boardList){
			log.info(boardDto.toString());
		}

	}

	@Test
	public void updateBoardTest(){
		// given
		BoardDto dto = new BoardDto();
		dto.setId(1L);
		dto.setName("Board Name");
		dto.setExplain("Board Explanation");
		dto.setType(BoardType.NOTICE);
		boardService.createBoard(dto);

		//when
		dto.setExplain("Board Explanation2");
		BoardDto result = boardService.updateBoard(1L,dto);

		//then
		assertThat(result.getExplain()).isEqualTo("Board Explanation2");
	}

	@Test
	public void deleteBoardTest(){
		// given
		BoardDto dto = new BoardDto();
		dto.setId(1L);
		dto.setName("Board Name");
		dto.setExplain("Board Explanation");
		dto.setType(BoardType.NOTICE);
		boardService.createBoard(dto);

		// when
		 boardService.deleteBoard(1L);

		// then
		log.info(boardService.readBoardAll().toString());

	}


}
