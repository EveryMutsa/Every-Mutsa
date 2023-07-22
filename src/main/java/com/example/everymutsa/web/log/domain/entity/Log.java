package com.example.everymutsa.web.log.domain.entity;

import com.example.everymutsa.common.BaseEntity;

import com.example.everymutsa.web.log.domain.dto.LogRequest;
import com.example.everymutsa.web.school.domain.entity.School;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "log")
public class Log extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String content;

	@JsonIgnoreProperties({"hibernateLazyInitializer"})
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id")
	private School school;


	public static Log fromDto(LogRequest request){
		Log log = new Log();
		log.id = request.getId();
		log.content = request.getContent();
		return log;
	}

	public void setContents(LogRequest request){
		this.content = request.getContent();
  }
	public void setSchool(School school) {
		this.school = school;
	}

	@Builder
	public Log(String content, School school) {
		this.content = content;
		this.school = school;
	}
}
