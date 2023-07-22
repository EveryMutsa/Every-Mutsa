package com.example.everymutsa.web.school.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SchoolUpdate {
	private Integer attendance;
	private Integer ready;
	private Integer pace;

	@Builder
	public SchoolUpdate(Integer attendance, Integer ready, Integer pace) {
		this.attendance = attendance;
		this.ready = ready;
		this.pace = pace;
	}
}
