package com.example.everymutsa.web.log.domain.entity;

import com.example.everymutsa.common.BaseEntity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "log")
public class Log extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "content")
	private String content;

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "school_id")
//	private School school;
}
