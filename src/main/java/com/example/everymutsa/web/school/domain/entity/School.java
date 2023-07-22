package com.example.everymutsa.web.school.domain.entity;

import java.time.Instant;

import com.example.everymutsa.common.BaseEntity;
import com.example.everymutsa.web.school.domain.dto.SchoolUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class School extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "school_id")
	// Default Value
	private Long id;

	private String name;

	private Instant endDate; // LocalDateTime 종료일

	private Integer attendance;

	private Integer ready;

	private Integer pace;

	private String discord;
	private String notion;

	public void changePeriod(Instant endDate) {
		this.endDate = endDate;
	}

	public void updateStatus(SchoolUpdate update) {
		this.attendance = update.getAttendance();
		this.ready = update.getReady();
		this.pace = update.getPace();
	}

	@Builder
	public School(String name, Instant endDate, Integer attendance, Integer ready, Integer pace, String discord,
		String notion) {
		this.name = name;
		this.endDate = endDate;
		this.attendance = attendance;
		this.ready = ready;
		this.pace = pace;
		this.discord = discord;
		this.notion = notion;
	}
}
