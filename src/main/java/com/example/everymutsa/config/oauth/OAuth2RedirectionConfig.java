package com.example.everymutsa.config.oauth;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ConfigurationProperties(prefix = "app.oauth2")
public class OAuth2RedirectionConfig {
	private List<String> authorizedRedirectUris;

}
