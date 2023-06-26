package com.example.everymutsa.web.user.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
public class UserUpdateRequest {
	String name;
	String nickName;
	MultipartFile profileImage;

	@Builder
	public UserUpdateRequest(String name, String nickName, MultipartFile profileImage) {
		this.name = name;
		this.nickName = nickName;
		this.profileImage = profileImage;
	}
}
