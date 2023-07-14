package com.example.everymutsa.web.school.contoller;

import com.example.everymutsa.web.school.domain.dto.SchoolDto;
import com.example.everymutsa.web.school.domain.entity.School;
import com.example.everymutsa.web.school.service.SchoolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @GetMapping("/{id}")
    public SchoolDto readById(
            @PathVariable Long id
    ) {
        School school = schoolService.findById(id);
        return SchoolDto.fromEntity(school);
    }

    @GetMapping
    public List<SchoolDto> readAllById(
            Long id
    ) {
        List<School> schools = schoolService.findAllById(id);
        return schools.stream()
                .map(SchoolDto::fromEntity)
                .collect(Collectors.toList());
    }

    @PostMapping
    public SchoolDto create(
            @RequestBody SchoolDto dto
    ) {
        Long id = schoolService.save(dto);
        return SchoolDto.fromEntity(schoolService.findById(id));
    }

    @PostMapping("/{id}")
    public SchoolDto update(
            @PathVariable Long id,
            @RequestBody SchoolDto dto
    ) {
        return schoolService.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable Long id
    ) {
        schoolService.remove(id);
        return ResponseEntity.ok("성공적으로 삭제되었습니다.");
    }
}
