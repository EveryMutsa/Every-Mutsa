package com.example.everymutsa.web.member.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;

@Data
public class MemberUpdateRequest {
	String name;
	String nickName;
	MultipartFile profileImage;

	@Builder
	public MemberUpdateRequest(String name, String nickName, MultipartFile profileImage) {
		this.name = name;
		this.nickName = nickName;
		this.profileImage = profileImage;
	}
}
