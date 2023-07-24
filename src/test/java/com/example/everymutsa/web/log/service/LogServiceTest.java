// package com.example.everymutsa.web.log.service;
//
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit.jupiter.SpringExtension;
// import org.springframework.transaction.annotation.Transactional;
//
// import com.example.everymutsa.web.log.domain.dto.LogRequest;
// import com.example.everymutsa.web.log.domain.entity.Log;
// import com.example.everymutsa.web.log.repository.LogRepository;
// import com.example.everymutsa.web.school.service.SchoolService;
//
// import jakarta.persistence.EntityManager;
//
// @ExtendWith(SpringExtension.class)
// @SpringBootTest
// @Transactional
// class LogServiceTest {
// 	@Autowired
// 	EntityManager em;
//
// 	@Autowired
// 	LogRepository logRepository;
//
// 	@Autowired
// 	LogService logService;
// 	SchoolService schoolService;
//
// 	@Test
// 	void changeDto(){
// 		//given
// 		LogRequest logDto = new LogRequest();
// 		logDto.setId(1L);
// 		logDto.setContent("contents");
// 		logDto.setSchoolId(1L);
//
// 	}
// 	@Test
// 	void findAll() {
// 		System.out.println(logService.findAll());
// 	}
//
// 	@Test
// 	void findOne() {
// 		//given
// 		//when
// 		Log log = logService.findOne(5L);
// 		//then
// 		System.out.println(log);
// 	}
//
// 	@Test
// 	void save() {
// 		//given
// 		//when
// 		//then
// 	}
//
// 	@Test
// 	void update() {
// 		// Log log = logService.findOne(5L);
// 		// log.setContent("내용 수정");
// 		// logService.update(log);
// 		// System.out.println(logService.findOne(5L).getCreatedAt());
// 		// System.out.println(logService.findOne(5L).getUpdatedAt());
// 	}
//
// 	@Test
// 	void remove() {
// 		logService.remove(5L);
// 		System.out.println(logService.findAll());
// 	}
// }