package com.example.everymutsa.api.auth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInRequest {
	private String email;
	private String password;

	@Builder
	public SignInRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
