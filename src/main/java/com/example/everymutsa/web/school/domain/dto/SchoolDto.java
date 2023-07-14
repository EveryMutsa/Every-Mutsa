package com.example.everymutsa.web.school.domain.dto;

import com.example.everymutsa.web.school.domain.entity.School;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SchoolDto {
    private Long id;
    private String name;
    private Instant period;
    private Integer total;
    private Integer completed;
    private Integer pace;
    private String discord;
    private String notion;

    public SchoolDto(Long id, String name, Instant period, Integer total, Integer completed, Integer pace, String discord, String notion) {
        this.id = id;
        this.name = name;
        this.period = period;
        this.total = total;
        this.completed = completed;
        this.pace = pace;
        this.discord = discord;
        this.notion = notion;
    }

    public static SchoolDto fromEntity(School school) {
        SchoolDto dto = new SchoolDto();
        dto.id = school.getId();
        dto.name = school.getName();
        dto.period = school.getPeriod();
        dto.total = school.getTotal();
        dto.completed = school.getCompleted();
        dto.pace = school.getPace();
        dto.discord = school.getDiscord();
        dto.notion = school.getNotion();
        return dto;
    }
}
