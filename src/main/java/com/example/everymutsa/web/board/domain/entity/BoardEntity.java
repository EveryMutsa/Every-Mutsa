package com.example.everymutsa.web.board.domain.entity;

import com.example.everymutsa.web.board.domain.enums.BoardType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "board")
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 128)
	private String name;

	@Column(length = 255)
	private String explain;

	@Enumerated(EnumType.STRING)
	private BoardType type;

}

/*
created_at : BaseEntity 로 상속받을 예정
*/

//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "school_id")
//	private School school;

// @OneToMany(mappedBy = "board")
// private List<Post> posts = new ArrayList<>();