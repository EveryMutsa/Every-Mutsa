package com.example.everymutsa.web.user.service;

import static com.example.everymutsa.common.fixture.UserFixture.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.example.everymutsa.api.auth.dto.SignUpRequest;
import com.example.everymutsa.api.file.dto.FileResponse;
import com.example.everymutsa.api.file.service.FileService;
import com.example.everymutsa.common.fixture.FileResponseFixture;
import com.example.everymutsa.common.fixture.UserFixture;
import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.member.dto.MemberMapper;
import com.example.everymutsa.web.member.dto.MemberResponse;
import com.example.everymutsa.web.member.repository.MemberRepository;
import com.example.everymutsa.web.member.service.MemberService;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@Mock
	FileService fileService;
	@Mock
	MemberRepository userRepository;
	@Mock
	MemberMapper memberMapper;

	@InjectMocks
	MemberService memberService;

	private static final FileResponse TEST_FILE_RESPONSE = FileResponseFixture.createFileResponse();
	private static final Member TEST_MEMBER_WITH_ID = UserFixture.createUserWithId(TEST_ID);

	@Test
	@DisplayName("회원 가입 테스트")
	void saveTest() throws Exception {
		//given
		when(fileService.storeAsHash(any())).thenReturn(TEST_FILE_RESPONSE);
		when(memberMapper.toEntity(any(SignUpRequest.class))).thenReturn(TEST_MEMBER);
		//when
		memberService.save(TEST_USER_REQUEST);
		//then
		verify(userRepository, times(1)).save(any(Member.class));
	}

	@Test
	@DisplayName("Update객체를 통해 User의 정보를 수정할 수 있어야 한다.")
	void updateOneTest() throws Exception {
		//given
		when(userRepository.findByEmailOrThrow(anyString())).thenReturn(TEST_MEMBER);
		when(fileService.storeAsHash(any(MultipartFile.class))).thenReturn(TEST_FILE_RESPONSE);
		//when
		MemberResponse memberResponse = memberService.updateOne(TEST_EMAIL, TEST_UPDATE_USER_REQUEST);
		//then
		verify(memberMapper, times(1)).toDto(TEST_MEMBER);
	}

	@Test
	@DisplayName("회원을 id를 통해 조회할 수 있어야한다.")
	void findByIdTest() throws Exception {
		//given
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(TEST_MEMBER_WITH_ID));
		//when
		memberService.findById(TEST_ID);
		//then
		assertThat(TEST_MEMBER_WITH_ID.getId()).isEqualTo(TEST_ID);
	}

}