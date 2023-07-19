package com.example.everymutsa.web.log.domain.dto;

import com.example.everymutsa.web.log.domain.entity.Log;

import lombok.Data;

@Data
public class LogDto {
	private Long id;
	private String content;



	public static LogDto fromEntity(Log log){
		LogDto logDto = new LogDto();
		logDto.id 		= log.getId();
		logDto.content 	= log.getContent();
		return logDto;
	}

}
