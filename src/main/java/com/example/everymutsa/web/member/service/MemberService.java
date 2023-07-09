package com.example.everymutsa.web.member.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.everymutsa.api.auth.dto.SignUpRequest;
import com.example.everymutsa.api.file.dto.FileResponse;
import com.example.everymutsa.api.file.service.FileService;
import com.example.everymutsa.common.exception.AuthException;
import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.web.member.domain.Member;
import com.example.everymutsa.web.member.dto.MemberMapper;
import com.example.everymutsa.web.member.dto.MemberResponse;
import com.example.everymutsa.web.member.dto.MemberUpdateRequest;
import com.example.everymutsa.web.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository userRepository;
	private final FileService fileService;
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	public MemberResponse save(SignUpRequest signUpRequest) {
		try {
			Member member = memberMapper.toEntity(signUpRequest);
			member.encodePassword(passwordEncoder);
			return memberMapper.toDto(userRepository.save(member));
		} catch (DataIntegrityViolationException de) {
			throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
		}
	}

	public Member findById(Long id) {
		return userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
	}

	public Member findByEmail(String email) {
		return userRepository.findByEmailOrThrow(email);
	}

	public MemberResponse findOne(Long id) {
		return memberMapper.toDto(findById(id));
	}

	@Transactional
	public MemberResponse updateOne(String email, MemberUpdateRequest updateRequest) {
		Member foundMember = userRepository.findByEmailOrThrow(email);
		FileResponse fileResponse = fileService.storeAsHash(updateRequest.getProfileImage());
		String hashedName = fileResponse.getFileName();
		foundMember.updateInfo(updateRequest.getName(), updateRequest.getNickName(), hashedName);
		return memberMapper.toDto(foundMember);
	}

	public void remove(String email) {
		if (userRepository.findByEmail(email).isEmpty()) {
			throw new EntityNotFoundException(ErrorCode.USER_NOT_FOUND);
		}
		userRepository.deleteByEmail(email);
	}
}
