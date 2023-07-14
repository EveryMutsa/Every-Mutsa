package com.example.everymutsa.web.school.domain.entity;

import com.example.everymutsa.common.BaseEntity;
import com.example.everymutsa.web.school.domain.dto.SchoolDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class School extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    // Default Value
    private Long id;
    @Column(length = 32)
    private String name;
    @Column
    private Instant period; // LocalDateTime 종료일

    @ColumnDefault("0")
    private Integer total; // 출석한 총 학생수
    @ColumnDefault("0")
    private Integer completed; // 실습 완료한 학생 수
    @ColumnDefault("50")
    private Integer pace; // 수업 속도

    // Default Value
    @Column(length = 255)
    private String discord;
    @Column(length = 255)
    private String notion;

    public static School toEntity(SchoolDto dto) {
        School school = new School();
        school.setId(dto.getId());
        school.setName(dto.getName());
        school.setPeriod(dto.getPeriod());
        school.setTotal(dto.getTotal());
        school.setCompleted(dto.getCompleted());
        school.setPace(dto.getPace());
        school.setDiscord(dto.getDiscord());
        school.setNotion(dto.getNotion());
        return school;
    }
}
