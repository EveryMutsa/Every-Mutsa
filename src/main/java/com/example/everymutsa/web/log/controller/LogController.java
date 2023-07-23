package com.example.everymutsa.web.log.controller;

import com.example.everymutsa.web.log.domain.dto.LogRequest;
import com.example.everymutsa.web.log.domain.dto.LogResponse;
import com.example.everymutsa.web.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/log")
public class LogController {
    final LogService logService;


    /** date 로그 : school 와 date 날짜 기준 검색 */
    @GetMapping("/school/{school_id}")
    public List<LogResponse> readDateLogs(
            @PathVariable Long school_id,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yy-MM-dd") LocalDate date)
    {
        if (date == null) {date = LocalDate.now();}
        return logService.readAllBySchool(school_id, date);
    }

    /** Log id 기준 검색 */
    @GetMapping("/{lid}")
    public LogResponse readLogOne(@PathVariable Long lid){
        return logService.readOne(lid);
    }

    /** Log 생성 */
    @PostMapping("")
    public LogResponse saveLog(@ModelAttribute("Log") LogRequest request){
        return logService.save(request);
    }

    /** Log 수정 */
    @PutMapping("")
    public LogResponse updateLog(@ModelAttribute("Log") LogRequest request){
        return logService.update(request);
    }

    /** Log 삭제 */
    @DeleteMapping("/{lid}")
    public ResponseEntity<String> removeLog(@PathVariable Long lid){
        logService.remove(lid);
        return ResponseEntity.ok("Deletion successful");
    }
}
