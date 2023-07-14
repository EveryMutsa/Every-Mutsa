package com.example.everymutsa.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ErrorCode {
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U_001", "회원을 찾을 수 없습니다."),
	FILE_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F_OO1", "파일 업로드 중 오류가 발생하였습니다."),
	FILE_HASHING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F_OO2", "파일 해싱 중 오류가 발생하였습니다."),
	FILE_READING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F_OO3", "파일 읽기 중 오류가 발생하였습니다."),
	FILE_STORAGE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "F_OO4", "파일 저장 중 오류가 발생하였습니다."),
	FILE_SIZE_ZERO(HttpStatus.BAD_REQUEST, "F_005", "파일 크기가 0입니다."),
	INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "S_002", "잘못된 요청 값입니다."),
	FILE_NOT_FOUND(HttpStatus.NOT_FOUND, "F_006", "파일을 찾을 수 없습니다."),
	DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "AU_001", "이미 사용중인 이메일입니다."),
	REFRESH_TOKEN_INVALID(HttpStatus.BAD_REQUEST, "AU_002", "유효하지 않은 토큰입니다."),
	UNSUPPORTED_OAUTH2_PROVIDER(HttpStatus.NOT_ACCEPTABLE, "AU_003", "지원하지 않는 로그인 수단입니다."),
	SERIALIZE_FAILURE(HttpStatus.NOT_IMPLEMENTED, "I_001", "직렬화 실패"),
	DESERIALIZE_FAILURE(HttpStatus.NOT_IMPLEMENTED, "I_002", "역직렬화 실패"),
	COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "C_001", "댓글을 찾을 수 없습니다.");

	ErrorCode(HttpStatus httpStatus, String code, String message) {
		this.httpStatus = httpStatus;
		this.code = code;
		this.message = message;
	}

	private final HttpStatus httpStatus;
	private final String code;
	private final String message;
}
