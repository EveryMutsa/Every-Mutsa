package com.example.everymutsa.web.school.repository;

import com.example.everymutsa.web.school.domain.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {
    List<School> findAllById(Long id);
}
