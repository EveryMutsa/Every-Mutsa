package com.example.everymutsa.api.auth.service;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.everymutsa.config.jwt.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenService {

	private final RedisTemplate<String, String> redisTemplate;
	private final JwtTokenProvider tokenProvider;

	public void save(String email, String token) {
		redisTemplate.opsForValue()
			.set(email, token, tokenProvider.REFRESH_TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);
	}

	public void deleteTokenByEmail(String email) {
		redisTemplate.delete(email);
	}

	public boolean validateToken(String token) {
		if (!tokenProvider.validateToken(token)) {
			return false;
		}
		String foundToken = redisTemplate.opsForValue().get(tokenProvider.getUsernameFromToken(token));
		return token != null && foundToken.equals(token);
	}

}
