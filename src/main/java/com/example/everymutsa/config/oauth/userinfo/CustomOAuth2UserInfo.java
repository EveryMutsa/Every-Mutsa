package com.example.everymutsa.config.oauth.userinfo;

import java.util.Map;

import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.common.exception.InvalidValueException;

public abstract class CustomOAuth2UserInfo {
	protected final Map<String, Object> attributes;
	protected String email;
	protected String name;

	protected CustomOAuth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
		initProperties();
	}

	public abstract void initProperties();

	public abstract CustomOAuth2Provider getProvider();

	public abstract String getEmail();

	public abstract String getName();

	public Map<String, Object> getAttributes() {
		return this.attributes;
	}

	public static CustomOAuth2UserInfo of(String registrationId, Map<String, Object> attributes) {
		if (CustomOAuth2Provider.KAKAO.equalsWith(registrationId)) {
			return new KakaoOAuth2UserInfo(attributes);
		} else if (CustomOAuth2Provider.GOOGLE.equalsWith(registrationId)) {
			return new GoogleOAuth2UserInfo(attributes);
		} else {
			throw new InvalidValueException(ErrorCode.UNSUPPORTED_OAUTH2_PROVIDER);
		}
	}

}
