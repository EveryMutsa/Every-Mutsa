package com.example.everymutsa.web.log.controller;

import com.example.everymutsa.web.log.domain.entity.Log;
import com.example.everymutsa.web.log.service.LogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LogController {
    final LogService logService;
    @GetMapping("/log")
    public List<Log> readLogs(){
        return logService.findAll();
    }

    @GetMapping("/log/{lid}")
    public Log readLogOne(@PathVariable Long lid){
        try {
            return logService.findOne(lid);
        } catch (RuntimeException ex) {
            log.error("RuntimeException occurred: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }

    @PostMapping("/log")
    public Log saveLog(@ModelAttribute("Log") Log temp){
        return logService.save(temp);
    }

    @PutMapping("/log")
    public Log updateLog(@ModelAttribute("Log") Log temp){
        return logService.update(temp);
    }

    @DeleteMapping("/log/{lid}")
    public ResponseEntity<String> removeLog(@PathVariable Long lid){
        try {
            logService.remove(lid);
            return ResponseEntity.ok("Deletion successful");
        } catch (RuntimeException ex) {
            log.error("RuntimeException occurred: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }
    }


}
