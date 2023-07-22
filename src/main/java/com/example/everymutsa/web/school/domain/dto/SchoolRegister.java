package com.example.everymutsa.web.school.domain.dto;

import java.time.Instant;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolRegister {
	private Long id;
	private String name;
	private Instant period;
	private Integer attendance;
	private Integer ready;
	private Integer pace;
	private String discord;
	private String notion;

	@Builder
	public SchoolRegister(String name, Instant period, Integer attendance, Integer ready, Integer pace, String discord,
		String notion) {
		this.name = name;
		this.period = period;
		this.attendance = attendance;
		this.ready = ready;
		this.pace = pace;
		this.discord = discord;
		this.notion = notion;
	}
}
