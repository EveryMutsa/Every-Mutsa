package com.example.everymutsa.web.log.domain.dto;

import lombok.Data;

@Data
public class LogResponse {
	private Long id;
	private String content;
	private Long schoolId;
	private String schoolName;
	private String date;
	private String time;


}
