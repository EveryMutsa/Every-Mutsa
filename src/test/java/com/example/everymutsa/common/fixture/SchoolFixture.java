package com.example.everymutsa.common.fixture;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.test.util.ReflectionTestUtils;

import com.example.everymutsa.web.school.domain.dto.SchoolRegister;
import com.example.everymutsa.web.school.domain.dto.SchoolResponse;
import com.example.everymutsa.web.school.domain.dto.SchoolUpdate;
import com.example.everymutsa.web.school.domain.entity.School;

public class SchoolFixture {

	private static final Long TEST_SCHOOL_ID = 1L;
	private static final String TEST_SCHOOL_NAME = "BackEndSchool5th";
	private static final Instant TEST_END_DATE = Instant.now().plus(3600000, ChronoUnit.HOURS);
	private static final Integer TEST_ATTENDANCE = 120;
	private static final Integer TEST_READY_STUDENTS = 40;
	private static final Integer TEST_PACE_RATE = 19;
	private static final String TEST_DISCORD_URL = "www.ccc.discord.com";
	private static final String TEST_NOTION_URL = "www.notion.io.com";

	public static School createSchool() {
		return School.builder()
			.name(TEST_SCHOOL_NAME)
			.attendance(TEST_ATTENDANCE)
			.pace(TEST_PACE_RATE)
			.ready(TEST_READY_STUDENTS)
			.discord(TEST_DISCORD_URL)
			.notion(TEST_NOTION_URL)
			.build();

	}

	public static SchoolRegister createSchoolRegister() {
		return SchoolRegister.builder()
			.name(TEST_SCHOOL_NAME)
			.attendance(TEST_ATTENDANCE)
			.pace(TEST_PACE_RATE)
			.ready(TEST_READY_STUDENTS)
			.discord(TEST_DISCORD_URL)
			.notion(TEST_NOTION_URL)
			.build();
	}

	public static SchoolUpdate createSchoolUpdate() {
		return SchoolUpdate.builder()
			.ready(TEST_READY_STUDENTS)
			.attendance(TEST_ATTENDANCE)
			.pace(TEST_PACE_RATE)
			.build();
	}

	public static SchoolResponse createSchoolResponse() {
		return SchoolResponse.builder()
			.name(TEST_SCHOOL_NAME)
			.attendance(TEST_ATTENDANCE)
			.pace(TEST_PACE_RATE)
			.ready(TEST_READY_STUDENTS)
			.discord(TEST_DISCORD_URL)
			.notion(TEST_NOTION_URL)
			.build();
	}

	public static School createSchoolWithId() {
		School school = createSchool();
		ReflectionTestUtils.setField(school, "id", TEST_SCHOOL_ID);
		return school;
	}

}
