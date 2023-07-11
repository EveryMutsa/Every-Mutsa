package com.example.everymutsa.config.oauth.handlers;

import static com.example.everymutsa.config.oauth.handlers.CustomOAuth2AuthorizationRequestRepsitory.*;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.everymutsa.api.auth.service.JwtTokenService;
import com.example.everymutsa.common.exception.BusinessException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.config.oauth.CustomOAuth2UserPrincipal;
import com.example.everymutsa.config.oauth.OAuth2RedirectionConfig;
import com.example.everymutsa.utils.CookieUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
	private final JwtTokenService tokenService;
	private final OAuth2RedirectionConfig oAuth2RedirectionConfig;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authentication) throws IOException, ServletException {
		String targetUrl = determineTargetUrl(request, response, authentication);
		if (response.isCommitted()) {
			log.debug("response is already committed");
			return;
		}
		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) {
		Optional<String> redirectUrl = CookieUtils.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
			.map(Cookie::getValue);

		if (redirectUrl.isPresent() && isAuthorizedRedirectUri(redirectUrl.get())) {
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}
		String targetUrl = redirectUrl.orElse(getDefaultTargetUrl());
		if (authentication.getPrincipal() instanceof CustomOAuth2UserPrincipal principal) {
			String email = principal.getUsername();
			var tokenResponse = tokenService.generateToken(email, authentication);
			return UriComponentsBuilder.fromUriString(targetUrl)
				.queryParam("access_token", tokenResponse.accessToken())
				.queryParam("refresh_token", tokenResponse.refreshToken())
				.build().toUriString();
		} else {
			throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
		}
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		CustomOAuth2AuthorizationRequestRepsitory.removeAuthorizationRequestResposne(request, response);
	}

	private boolean isAuthorizedRedirectUri(String uri) {
		URI clientRedirectUri = URI.create(uri);
		return oAuth2RedirectionConfig.getAuthorizedRedirectUris()
			.stream()
			.anyMatch(authorizedRedirectUri -> {
				URI authorizedUri = URI.create(authorizedRedirectUri);
				return authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
					&& authorizedUri.getPort() == clientRedirectUri.getPort();
			});
	}

}
