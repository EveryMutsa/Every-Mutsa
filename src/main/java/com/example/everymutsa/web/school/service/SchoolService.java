package com.example.everymutsa.web.school.service;

import com.example.everymutsa.web.school.domain.dto.SchoolDto;
import com.example.everymutsa.web.school.domain.entity.School;
import com.example.everymutsa.web.school.repository.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class SchoolService {
    private final SchoolRepository schoolRepository;

    public School findById(Long id) {
        return schoolRepository.findById(id)
                .orElseThrow();
    }

    public List<School> findAllById(Long id) {
        return schoolRepository.findAllById(id);
    }

    // save
    @Transactional
    public Long save(SchoolDto dto) {
        School school = School.toEntity(dto);
        schoolRepository.save(school);
        return school.getId();
    }

    @Transactional
    public SchoolDto update(Long id, SchoolDto dto) {
        findById(id);
        School school = School.toEntity(dto);
        return SchoolDto.fromEntity(school);
    }

    @Transactional
    public void remove(Long id) {
        schoolRepository.deleteById(id);
    }
}
