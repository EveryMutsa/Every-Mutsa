package com.example.everymutsa.web.log.service;

import com.example.everymutsa.web.log.domain.entity.Log;
import com.example.everymutsa.web.log.repository.LogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> findAll() {
        return logRepository.findAll();
    }

    public Log findOne(Long lid) {
        Optional<Log> log = logRepository.findById(lid);
        if(log.isPresent()) return log.get();
        else throw new RuntimeException(new RuntimeException("해당 id(" + lid + ")의 로그를 찾을 수 없습니다."));
    }

    public Log save(Log log){
        return logRepository.save(log);
    }

    public Log update(Log log){
        Log tempLog = findOne(log.getId());
        tempLog.setContent(log.getContent());
        return logRepository.save(tempLog);
    }

    public void remove(Long lid){
        logRepository.delete(findOne(lid));
    }
}
