package com.example.everymutsa.web.member.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.everymutsa.api.auth.dto.SignUpRequest;
import com.example.everymutsa.web.member.domain.Member;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	MemberMapper INSTANCE = Mappers.getMapper(MemberMapper.class);

	@Mapping(target = "hashedProfile", ignore = true)
	Member toEntity(SignUpRequest signUpRequest);

	MemberResponse toDto(Member member);

}
