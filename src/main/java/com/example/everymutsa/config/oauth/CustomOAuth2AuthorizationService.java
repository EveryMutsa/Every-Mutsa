package com.example.everymutsa.config.oauth;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.everymutsa.config.oauth.userinfo.CustomOAuth2UserInfo;
import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.member.domain.Role;
import com.example.everymutsa.web.member.repository.MemberRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomOAuth2AuthorizationService extends DefaultOAuth2UserService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public CustomOAuth2AuthorizationService(MemberRepository memberRepository, @Lazy PasswordEncoder passwordEncoder) {
		this.memberRepository = memberRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		var providedInfo = super.loadUser(userRequest);
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		var userInfo = CustomOAuth2UserInfo.of(registrationId, providedInfo.getAttributes());
		Optional<Member> nullableMember = memberRepository.findByEmail(userInfo.getEmail());
		if (nullableMember.isEmpty()) {
			Member memberProxy = Member.builder()
				.email(userInfo.getEmail())
				.name(userInfo.getName())
				.password(createDummyPassword())
				.role(Role.STUDENT)
				.accessedAt(LocalDateTime.now())
				.build();
			memberRepository.save(memberProxy);
			return new CustomOAuth2UserPrincipal(memberProxy, userInfo);
		}
		return new CustomOAuth2UserPrincipal(nullableMember.get(), userInfo);
	}

	private String createDummyPassword() {
		return passwordEncoder.encode(UUID.randomUUID().toString().replace("-", ""));
	}

}

