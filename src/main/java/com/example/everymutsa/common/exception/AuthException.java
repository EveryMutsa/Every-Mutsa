package com.example.everymutsa.common.exception;

public class AuthException extends BusinessException {
	public AuthException(ErrorCode errorCode, String detail) {
		super(errorCode, detail);
	}

	public AuthException(ErrorCode errorCode) {
		super(errorCode);
	}
}
