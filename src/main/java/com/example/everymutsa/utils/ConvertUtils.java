package com.example.everymutsa.utils;

public class ConvertUtils {
	private ConvertUtils() {
		throw new IllegalStateException("인스턴스화 할 수 없습니다.");
	}

	public static <T> T uncheckedCast(Object object) {
		return (T)object;
	}
}
