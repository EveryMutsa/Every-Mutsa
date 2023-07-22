package com.example.everymutsa.web.log.controller;

import com.example.everymutsa.web.log.domain.dto.LogRequest;
import com.example.everymutsa.web.log.domain.dto.LogResponse;
import com.example.everymutsa.web.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/log")
public class LogController {
    final LogService logService;

    /** 전체 검색 */
    @GetMapping("")
    public List<LogResponse> readLogs(){
        return logService.getAll();
    }

    /** 오늘의 로그 : school 와 오늘 날짜 기준 검색 */
    @GetMapping("/school/{school_id}")
    public List<LogResponse> readTodayLogs(
        @PathVariable Long school_id )
    {
        return logService.getTodayListBySchool(school_id);
    }

    /** Log id 기준 검색 */
    @GetMapping("/{lid}")
    public LogResponse readLogOne(@PathVariable Long lid){
        return logService.getOne(lid);
    }

    @PostMapping("")
    public LogResponse saveLog(@ModelAttribute("Log") LogRequest request){
        return logService.save(request);
    }

    @PutMapping("")
    public LogResponse updateLog(@ModelAttribute("Log") LogRequest request){
        return logService.update(request);
    }

    @DeleteMapping("/{lid}")
    public ResponseEntity<String> removeLog(@PathVariable Long lid){
        logService.remove(lid);
        return ResponseEntity.ok("Deletion successful");
    }
}
