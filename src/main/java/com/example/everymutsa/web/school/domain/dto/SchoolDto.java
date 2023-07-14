package com.example.everymutsa.web.school.domain.dto;

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
}
