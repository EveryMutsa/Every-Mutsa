package com.example.everymutsa.web.school.service;

import com.example.everymutsa.web.post.domain.dto.PostParam;
import com.example.everymutsa.web.post.domain.entity.Post;
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

    // save
    @Transactional
    public Long save(SchoolDto dto) {
        School school = School.createPost(dto);
        schoolRepository.save(school);
        return school.getId();
    }

}
