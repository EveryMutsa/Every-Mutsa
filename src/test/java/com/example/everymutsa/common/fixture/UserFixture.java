package com.example.everymutsa.common.fixture;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import com.example.everymutsa.api.auth.dto.SignUpRequest;
import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.member.domain.Role;
import com.example.everymutsa.web.member.dto.MemberUpdateRequest;

public class UserFixture {
	public static final Long TEST_ID = 1L;
	public static final String TEST_EMAIL = "test@test.com";
	public static final String TEST_PASSWORD = "test123456";
	public static final String TEST_NAME = "testUser";
	public static final String TEST_NICK_NAME = "hireo";
	public static final Role TEST_ROLE = Role.STUDENT;

	public static final MockMultipartFile TEST_IMAGE_FILE = new MockMultipartFile("testfile", "testfile.png",
		"application/x-www-form-urlencoded", "testfile.png".getBytes());

	public static final String TEST_HASHED_PROFILE = "4ee84a82b5e4c9e6651b13fd27dcf615e427ec584929f2cef7167aa99151a77a.png";
	public static final String TEST_UPDATED_HASHED_PROFILE = "1912f4b56f57e84356774da98307f7365b9f7aec1fdd6cb7bcd1010610185b07.png";
	public static final String TEST_UPDATE_NAME = "horntail";
	public static final String TEST_UPDATE_NICK_NAME = "usa";
	public static final MockMultipartFile TEST_UPDATE_IMAGE_FILE = new MockMultipartFile("test_imagefile",
		"test_imagefile.png",
		"application/x-www-form-urlencoded", "profile.png".getBytes());

	public static final MemberUpdateRequest TEST_UPDATE_USER_REQUEST = MemberUpdateRequest
		.builder()
		.name(TEST_UPDATE_NAME)
		.nickName(TEST_UPDATE_NICK_NAME)
		.profileImage(TEST_UPDATE_IMAGE_FILE)
		.build();

	public static Member createUser() {
		return Member.builder()
			.name(TEST_NAME)
			.nickName(TEST_NICK_NAME)
			.email(TEST_EMAIL)
			.password(TEST_PASSWORD)
			.hashedProfile(TEST_HASHED_PROFILE)
			.role(TEST_ROLE)
			.build();
	}

	public static final SignUpRequest TEST_USER_REQUEST = SignUpRequest.builder()
		.name(TEST_NAME)
		.email(TEST_EMAIL)
		.password(TEST_PASSWORD)
		.nickName(TEST_NICK_NAME)
		.profileImage(TEST_IMAGE_FILE)
		.build();

	public static final Member TEST_MEMBER = Member.builder()
		.name(TEST_NAME)
		.nickName(TEST_NICK_NAME)
		.email(TEST_EMAIL)
		.password(TEST_PASSWORD)
		.hashedProfile(TEST_HASHED_PROFILE)
		.role(TEST_ROLE)
		.build();

	public static Member createUserWithId(Long id) {
		var user = createUser();
		ReflectionTestUtils.setField(user, "id", id);
		return user;
	}

}
