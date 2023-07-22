package com.example.everymutsa.web.school.domain.dto;

import java.time.Instant;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SchoolResponse {
	private Long id;
	private String name;
	private Instant endDate;
	private Integer attendance;
	private Integer ready;
	private Integer pace;
	private String discord;
	private String notion;

	@Builder
	public SchoolResponse(Long id, String name, Instant endDate, Integer attendance, Integer ready, Integer pace,
		String discord, String notion) {
		this.id = id;
		this.name = name;
		this.endDate = endDate;
		this.attendance = attendance;
		this.ready = ready;
		this.pace = pace;
		this.discord = discord;
		this.notion = notion;
	}
}
