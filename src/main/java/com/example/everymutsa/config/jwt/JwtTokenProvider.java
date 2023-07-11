package com.example.everymutsa.config.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	private final String base64Secret;
	public final Long ACCESS_TOKEN_EXPIRATION_TIME;
	public final Long REFRESH_TOKEN_EXPIRATION_TIME;

	private static final String AUTHORITIES_KEY = "auth";
	private Key key;

	public JwtTokenProvider(
		@Value("${security.jwt.base-64-secret}") String base64Secret,
		@Value("${security.jwt.access-expiration-time}") Long accessExpirationTime,
		@Value("${security.jwt.refresh-expiration-time}") Long refreshExpirationTime
	) {
		this.base64Secret = base64Secret;
		this.ACCESS_TOKEN_EXPIRATION_TIME = accessExpirationTime;
		this.REFRESH_TOKEN_EXPIRATION_TIME = refreshExpirationTime;
	}

	@PostConstruct
	public void init() {
		byte[] secreteKeyBytes = Decoders.BASE64.decode(base64Secret);
		this.key = Keys.hmacShaKeyFor(secreteKeyBytes);
	}

	private String createToken(Authentication authentication, long expirationTime) {
		String authorites = authentication.getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim(AUTHORITIES_KEY, authorites)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();
	}

	public String createAccessToken(Authentication authentication) {
		return createToken(authentication, ACCESS_TOKEN_EXPIRATION_TIME);
	}

	public String createRefreshToken(Authentication authentication) {
		return createToken(authentication, REFRESH_TOKEN_EXPIRATION_TIME);
	}

	public Authentication getAuthentication(String token) {
		Claims claims = getAllClaimsFromToken(token).getBody();
		Collection<? extends GrantedAuthority> authorities = Arrays.stream(
				Optional.ofNullable(claims.get(AUTHORITIES_KEY))
					.map(Object::toString)
					.orElse("")
					.split(","))
			.map(String::trim)
			.filter(auth -> !auth.isEmpty())
			.map(SimpleGrantedAuthority::new)
			.toList();
		User principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}

	public boolean validateToken(String token) {
		try {
			getAllClaimsFromToken(token);
			return true;
		} catch (JwtException ex) {
			log.trace("Invalid Jwt token trace : {}", ex.toString());
			return false;
		}
	}

	private Jws<Claims> getAllClaimsFromToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token);
	}

	public String getUsernameFromToken(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
	}

}
