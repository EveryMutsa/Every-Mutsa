package com.example.everymutsa.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshTokenRequest {

	@JsonProperty("refresh_token")
	private String refreshToken;

	@Builder
	public RefreshTokenRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}
