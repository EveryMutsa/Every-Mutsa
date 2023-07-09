package com.example.everymutsa.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.everymutsa.config.jwt.JwtAccessDeniedHandler;
import com.example.everymutsa.config.jwt.JwtAuthTokenFilter;
import com.example.everymutsa.config.jwt.JwtAuthenticationEntryPoint;
import com.example.everymutsa.config.jwt.JwtTokenProvider;
import com.example.everymutsa.config.oauth.CustomOAuth2AuthorizationService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider tokenProvider;
	private final CustomOAuth2AuthorizationService oAuth2AuthorizationService;
	private final JwtAccessDeniedHandler accessDeniedHandler;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web
			.ignoring()
			.requestMatchers("/favicon.ico", "/error")
			.requestMatchers(PathRequest.toH2Console());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
		return http
			//.securityMatcher("/api/**")
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(
				auth -> auth
					.requestMatchers("/api/auth/**", "/api/docs/**", "/auth/**").permitAll()
					.anyRequest().authenticated())
			.sessionManagement(
				session -> session
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler))
			.addFilterBefore(new JwtAuthTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
			.oauth2Login(
				oauth -> oauth
					.authorizationEndpoint(
						authorization -> authorization.baseUri("/auth/login/oauth2/authorize")
					)
					.redirectionEndpoint(
						redirection -> redirection.baseUri("/auth/login/oauth2/code/{code}")
					)
					.userInfoEndpoint(userInfo -> userInfo.userService(oAuth2AuthorizationService))
			)
			.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
