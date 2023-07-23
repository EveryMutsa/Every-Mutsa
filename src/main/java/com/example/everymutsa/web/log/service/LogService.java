package com.example.everymutsa.web.log.service;

import com.example.everymutsa.web.log.domain.dto.LogMapper;
import com.example.everymutsa.web.log.domain.dto.LogRequest;
import com.example.everymutsa.web.log.domain.dto.LogResponse;
import com.example.everymutsa.web.log.domain.entity.Log;
import com.example.everymutsa.web.log.repository.LogRepository;
import com.example.everymutsa.web.school.domain.entity.School;
import com.example.everymutsa.web.school.repository.SchoolRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LogService {
    private final LogRepository logRepository;
    private final SchoolRepository schoolRepository;
    private final LogMapper logMapper;

    public List<LogResponse> readAllBySchool(Long schoolId, LocalDate date){
        Instant [] days = getStartEnd(date);
        School school   = schoolRepository.findByIdOrThrow(schoolId);
        List<Log> logs  = logRepository.findByCreatedAtBetweenAndSchool(days[0], days[1], school);
        return logs.stream().map(logMapper :: toDto).toList();
    }

    private Instant[] getStartEnd(LocalDate date
    ){
        Instant[] dates = new Instant[2];
        dates[0] = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        dates[1] = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        return dates;
    }

    public LogResponse readOne(Long logId) {
        return logMapper.toDto(logRepository.findByIdOrThrow(logId));
    }

    public LogResponse save(LogRequest request){
        School school   = schoolRepository.findByIdOrThrow(request.getSchoolId());
        Log newLog = Log.fromDto(request);
        newLog.setSchool(school);
        newLog = logRepository.save(newLog);
        return logMapper.toDto(newLog);
    }

    @Transactional
    public LogResponse update(LogRequest request){
        Log updateLog = logRepository.findByIdOrThrow(request.getId());
        updateLog.setContents(request);
        return logMapper.toDto(updateLog);
    }

    public void remove(Long lid){
        logRepository.delete(logRepository.findByIdOrThrow(lid));
    }


}
