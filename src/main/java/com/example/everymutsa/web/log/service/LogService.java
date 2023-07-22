package com.example.everymutsa.web.log.service;

import com.example.everymutsa.common.exception.EntityNotFoundException;
import com.example.everymutsa.common.exception.ErrorCode;
import com.example.everymutsa.web.log.domain.dto.LogMapper;
import com.example.everymutsa.web.log.domain.dto.LogRequest;
import com.example.everymutsa.web.log.domain.dto.LogResponse;
import com.example.everymutsa.web.log.domain.entity.Log;
import com.example.everymutsa.web.log.repository.LogRepository;
import com.example.everymutsa.web.school.domain.entity.School;
import com.example.everymutsa.web.school.service.SchoolService;

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
    private final SchoolService schoolService;
    private final LogMapper logMapper;

    public List<LogResponse> getAll(){
        List<Log> logs = findAll();
        return logs.stream().map(logMapper :: toDto).toList();
    }

    public List<Log> findAll() {
        return logRepository.findAll();
    }

    public List<LogResponse> getTodayListBySchool(Long schoolId){
        return getListBySchool(schoolId, LocalDate.now());
    }

    private Instant[] getStartEnd(LocalDate date){
        Instant[] dates = new Instant[2];
        dates[0] = date.atStartOfDay().toInstant(ZoneOffset.UTC);
        dates[1] = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC);
        return dates;
    }

    public List<LogResponse> getListBySchool(Long schoolId, LocalDate date){
        Instant [] days = getStartEnd(date);
        School school   = schoolService.findById(schoolId);
        List<Log> logs  = logRepository.findByCreatedAtBetweenAndSchool(days[0], days[1], school);
        return logs.stream().map(logMapper :: toDto).toList();
    }

    public Log findOne(Long logId) {
        return logRepository.findById(logId).orElseThrow(() -> new EntityNotFoundException(ErrorCode.LOG_NOT_FOUND));
    }

    public LogResponse getOne(Long logId) {
        return logMapper.toDto(findOne(logId));
    }

    public LogResponse save(LogRequest request){
        School school = schoolService.findById(request.getSchoolId());
        Log newLog = Log.fromDto(request);
        newLog.setSchool(school);
        newLog = logRepository.save(newLog);
        return logMapper.toDto(newLog);
    }

    @Transactional
    public LogResponse update(LogRequest request){
        Log updateLog = findOne(request.getId());
        updateLog.setContents(request);
        return logMapper.toDto(updateLog);
    }

    public void remove(Long lid){
        logRepository.delete(findOne(lid));
    }
}
