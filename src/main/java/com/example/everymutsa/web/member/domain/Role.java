package com.example.everymutsa.web.member.domain;

public enum Role {
	STUDENT("ROLE_STUDENT"), MANAGER("ROLE_MANAGER"), ADMIN("ROLE_ADMIN");

	private String role;

	Role(String role) {
		this.role = role;
	}
}
