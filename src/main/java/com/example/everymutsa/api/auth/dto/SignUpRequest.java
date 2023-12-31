package com.example.everymutsa.api.auth.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.example.everymutsa.web.member.domain.Role;

import lombok.Builder;
import lombok.Data;

@Data
public class SignUpRequest {
	private String password;
	private String name;
	private String email;
	private String nickName;
	private LocalDateTime accessedAt;
	private MultipartFile profileImage;
	private Role role;

	@Builder
	public SignUpRequest(String password, String name, String email, String nickName, LocalDateTime accessedAt,
		MultipartFile profileImage, Role role) {
		this.password = password;
		this.name = name;
		this.email = email;
		this.nickName = nickName;
		this.accessedAt = accessedAt;
		this.profileImage = profileImage;
		this.role = role;
	}
}
