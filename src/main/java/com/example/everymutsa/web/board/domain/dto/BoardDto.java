package com.example.everymutsa.web.board.domain.dto;

import com.example.everymutsa.web.board.domain.entity.BoardEntity;

import com.example.everymutsa.web.board.domain.enums.BoardType;
import lombok.Data;

@Data
public class BoardDto {
	private Long id;
	private String name;
	private String explain;
	private BoardType type;

	public static BoardDto fromEntity(BoardEntity entity) {
		BoardDto dto = new BoardDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setExplain(entity.getExplain());
		dto.setType(entity.getType());
		return dto;
	}
}
