package com.example.everymutsa.web.board.domain.enums;

public enum BoardType {
	NOTICE("공지사항"),
	QUESTION("질문사항"),
	COMMUNITY("커뮤니티");

	private final String type;

	BoardType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}
}
