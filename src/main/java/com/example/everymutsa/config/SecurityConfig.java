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
import com.example.everymutsa.config.oauth.handlers.CustomOAuth2AuthenticationSuccessHandler;
import com.example.everymutsa.config.oauth.handlers.CustomOAuth2AuthorizationRequestRepsitory;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider tokenProvider;
	private final CustomOAuth2AuthorizationService oAuth2AuthorizationService;
	private final CustomOAuth2AuthorizationRequestRepsitory customOAuth2AuthorizationRequestRepsitory;
	private final CustomOAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
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
		return http.securityMatcher("/api/**")
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/**", "/api/oauth2/**").permitAll()
				.requestMatchers("/api/hello/**").hasRole("ADMIN")
				.anyRequest().authenticated())
			.exceptionHandling(handler -> handler
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler))
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(new JwtAuthTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
			.oauth2Login(oauth2 -> oauth2
				.userInfoEndpoint(userInfo -> userInfo
					.userService(oAuth2AuthorizationService))
				.authorizationEndpoint(auth -> auth
					.baseUri("/api/oauth2/authorize")
					.authorizationRequestRepository(customOAuth2AuthorizationRequestRepsitory))
				.redirectionEndpoint(redirection -> redirection
					.baseUri("/api/oauth2/callback/{registrationId}"))
				.successHandler(oAuth2AuthenticationSuccessHandler))
			.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
