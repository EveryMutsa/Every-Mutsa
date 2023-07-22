package com.example.everymutsa.web.log.domain.entity;

import com.example.everymutsa.common.BaseEntity;

import com.example.everymutsa.web.log.domain.dto.LogDto;
import com.example.everymutsa.web.school.domain.entity.School;
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

	@Column(name = "content")
	private String content;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id")
	private School school;


	public static Log fromDto(LogDto request){
		Log log = new Log();
		log.id = request.getId();
		log.content = request.getContent();
		return log;
	}

	public void setContents(LogDto request){
		this.content = request.getContent();
	}

	public void setSchool(School school){
		this.school = school;
	}
}
