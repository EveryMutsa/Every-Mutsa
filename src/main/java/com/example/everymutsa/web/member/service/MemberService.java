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
import com.example.everymutsa.web.school.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final SchoolRepository schoolRepository;
	private final FileService fileService;
	private final MemberMapper memberMapper;
	private final PasswordEncoder passwordEncoder;

	public MemberResponse save(SignUpRequest signUpRequest) {
		try {
			var member = memberMapper.toEntity(signUpRequest);
			member.encodePassword(passwordEncoder);
			var school = schoolRepository.findByIdOrThrow(signUpRequest.getSchoolId());
			member.registerSchool(school);
			return memberMapper.toDto(memberRepository.save(member));
		} catch (DataIntegrityViolationException de) {
			throw new AuthException(ErrorCode.DUPLICATED_EMAIL);
		}
	}

	@Transactional(readOnly = true)
	public MemberResponse readOne(Long id) {
		return memberMapper
			.toDto(memberRepository.findByIdOrThrow(id));
	}

	@Transactional(readOnly = true)
	public Member readOneByEmail(String email) {
		return memberRepository.findByEmailOrThrow(email);
	}

	public MemberResponse updateOne(String email, MemberUpdateRequest updateRequest) {
		Member foundMember = memberRepository.findByEmailOrThrow(email);
		FileResponse fileResponse = fileService.storeAsHash(updateRequest.getProfileImage());
		String hashedName = fileResponse.getFileName();
		foundMember.updateInfo(updateRequest.getName(), updateRequest.getNickName(), hashedName);
		return memberMapper.toDto(foundMember);
	}

	public void remove(String email) {
		if (memberRepository.findByEmail(email).isEmpty()) {
			throw new EntityNotFoundException(ErrorCode.ENTITY_NOT_FOUND);
		}
		memberRepository.deleteByEmail(email);
	}
}
