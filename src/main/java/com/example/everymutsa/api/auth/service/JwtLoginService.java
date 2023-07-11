package com.example.everymutsa.api.auth.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.everymutsa.api.auth.dto.SignInRequest;
import com.example.everymutsa.api.auth.dto.TokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtLoginService {
	private final JwtTokenService tokenService;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	public TokenResponse authenticateUser(SignInRequest request) {

		var authenticationToken = new UsernamePasswordAuthenticationToken(
			request.getEmail(), request.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);
		return tokenService.generateToken(request.getEmail(), authentication);
	}

}
