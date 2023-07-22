package com.example.everymutsa.web.school.domain.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.everymutsa.web.school.domain.entity.School;

@Mapper(componentModel = "spring")
public interface SchoolMapper {

	SchoolMapper INSTANCE = Mappers.getMapper(SchoolMapper.class);

	School toEntity(SchoolRegister dto);

	SchoolResponse toDto(School entity);
}
