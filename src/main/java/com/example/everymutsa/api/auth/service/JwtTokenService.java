package com.example.everymutsa.api.auth.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.everymutsa.api.auth.dto.RefreshTokenRequest;
import com.example.everymutsa.api.auth.dto.TokenResponse;
import com.example.everymutsa.common.exception.AuthException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.config.jwt.JwtTokenProvider;
import com.example.everymutsa.web.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class JwtTokenService {

	private final MemberRepository memberRepository;
	private final JwtTokenProvider tokenProvider;
	private final RefreshTokenService refreshTokenService;

	public String createAndSaveRefreshToken(String email, Authentication authentication) {
		String refreshToken = tokenProvider.createRefreshToken(authentication);
		refreshTokenService.deleteTokenByEmail(email);
		refreshTokenService.save(email, refreshToken);
		return refreshToken;
	}

	public TokenResponse generateToken(String email, Authentication authentication) {
		String accessToken = tokenProvider.createAccessToken(authentication);
		String refreshToken = createAndSaveRefreshToken(email, authentication);

		return new TokenResponse(accessToken, refreshToken);
	}

	public TokenResponse refreshJwtTokens(RefreshTokenRequest request) {
		String refreshToken = request.getRefreshToken();
		validateRefreshToken(refreshToken);
		Authentication authentication = tokenProvider.getAuthentication(refreshToken);
		return generateToken(authentication.getName(), authentication);
	}

	public void validateRefreshToken(String token) {
		if (!refreshTokenService.validateToken(token)) {
			throw new AuthException(ErrorCode.REFRESH_TOKEN_INVALID);
		}
	}
}
