package com.example.everymutsa.api.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.everymutsa.api.auth.dto.RefreshTokenRequest;
import com.example.everymutsa.api.auth.dto.SignInRequest;
import com.example.everymutsa.api.auth.dto.SignUpRequest;
import com.example.everymutsa.api.auth.dto.TokenResponse;
import com.example.everymutsa.api.auth.service.JwtLoginService;
import com.example.everymutsa.api.auth.service.JwtTokenService;
import com.example.everymutsa.web.member.dto.MemberResponse;
import com.example.everymutsa.web.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class ApiAuthController {

	private final JwtLoginService loginService;
	private final MemberService memberService;
	private final JwtTokenService tokenService;

	@PostMapping("/signin")
	public ResponseEntity<TokenResponse> signin(@RequestBody SignInRequest request) {
		return ResponseEntity.ok().body(loginService.authenticateUser(request));
	}

	@PostMapping("/signup")
	public ResponseEntity<MemberResponse> signup(@RequestBody SignUpRequest request) {
		MemberResponse savedInfo = memberService.save(request);
		return ResponseEntity.ok().body(savedInfo);
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
		TokenResponse tokenResponse = tokenService.refreshJwtTokens(request);
		return ResponseEntity.ok().body(tokenResponse);
	}

}
