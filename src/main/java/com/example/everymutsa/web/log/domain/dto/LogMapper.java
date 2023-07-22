package com.example.everymutsa.web.log.domain.dto;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.mapstruct.Mapper;

import com.example.everymutsa.web.log.domain.entity.Log;
@Mapper(componentModel = "spring")
public class LogMapper {
	ZoneId zoneId = ZoneId.of("Asia/Seoul");
	DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy/MM/dd").withZone(zoneId);
	DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm").withZone(zoneId);
	public LogResponse toDto(Log log) {
		LogResponse logResponse = new LogResponse();
		logResponse.setId(log.getId());
		logResponse.setContent(log.getContent());
		logResponse.setSchoolId(log.getSchool().getId());
		logResponse.setSchoolName(log.getSchool().getName());
		logResponse.setTime(log.getCreatedAt().atZone(ZoneOffset.UTC).format(timeFormatter));
		logResponse.setDate(log.getCreatedAt().atZone(ZoneOffset.UTC).format(dateFormatter));
		return logResponse;
	}
}