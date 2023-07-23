package com.example.everymutsa.web.board.domain.entity;

import com.example.everymutsa.common.BaseEntity;
import com.example.everymutsa.web.board.domain.enums.BoardType;

import com.example.everymutsa.web.post.domain.entity.Post;
import com.example.everymutsa.web.school.domain.entity.School;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "board")
public class Board extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 128)
	private String name;

	@Column(length = 255)
	private String explain;

	@Enumerated(EnumType.STRING)
	private BoardType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id")
	private School school;

	@OneToMany(mappedBy = "board")
	private List<Post> posts = new ArrayList<>();

}




