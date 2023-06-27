package com.example.everymutsa.web.user.dto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.everymutsa.web.user.domain.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "hashedProfile", ignore = true)
	User toEntity(UserSaveRequest userSaveRequest);

	UserResponse toDto(User user);

}
