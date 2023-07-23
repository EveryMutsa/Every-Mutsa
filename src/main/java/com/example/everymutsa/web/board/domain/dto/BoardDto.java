package com.example.everymutsa.web.board.domain.dto;

import com.example.everymutsa.web.board.domain.entity.Board;

import com.example.everymutsa.web.school.domain.entity.School;
import lombok.Data;

@Data
public class BoardDto {
	private Long id;
	private String name;
	private String explain;
	private String type;
	private School schoolId;

	public static BoardDto fromEntity(Board entity) {
		BoardDto dto = new BoardDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setExplain(entity.getExplain());
		dto.setType(entity.getType().getType());
		dto.setSchoolId(entity.getSchool());
		return dto;
	}
}
