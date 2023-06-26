package com.example.everymutsa.web.user.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {
	private String name;
	private String email;
	private String nickName;
	private LocalDateTime accessedAt;
	private String hashedProfile;

	@Builder
	public UserResponse(String name, String email, String nickName, LocalDateTime accessedAt, String hashedProfile) {
		this.name = name;
		this.email = email;
		this.nickName = nickName;
		this.accessedAt = accessedAt;
		this.hashedProfile = hashedProfile;
	}
}
