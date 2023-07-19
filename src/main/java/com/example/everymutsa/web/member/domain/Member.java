package com.example.everymutsa.web.member.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.everymutsa.common.BaseEntity;
import com.example.everymutsa.web.comment.domain.entity.Comment;
import com.example.everymutsa.web.post.domain.entity.Post;
import com.example.everymutsa.web.school.domain.entity.School;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "member")
public class Member extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	Long id;
	@Column(nullable = false)
	String password;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	private String nickName;
	private LocalDateTime accessedAt;
	private String hashedProfile;
	@Enumerated(EnumType.STRING)
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id", nullable = false)
	private School school;

	@OneToMany(mappedBy = "member")
	private List<Post> posts = new ArrayList<>();

	@OneToMany(mappedBy = "member")
	private List<Comment> comments = new ArrayList<>();

	public void changeProfile(String hashedProfile) {
		this.hashedProfile = hashedProfile;
	}

	public void changeNickName(String newNickName) {
		this.nickName = newNickName;
	}

	public void updateInfo(String name, String nickName, String hashedProfile) {
		this.name = name;
		this.nickName = nickName;
		this.hashedProfile = hashedProfile;
	}

	/*SpringSecurity 사용 이전 약식으로 구현*/
	@PostConstruct
	public void encodePassword(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(password);
	}

	@Builder
	public Member(String password, String name, String email, String nickName, LocalDateTime accessedAt,
		String hashedProfile,
		Role role) {
		this.password = password;
		this.name = name;
		this.email = email;
		this.nickName = nickName;
		this.accessedAt = accessedAt;
		this.hashedProfile = hashedProfile;
		this.role = role;
	}
}
