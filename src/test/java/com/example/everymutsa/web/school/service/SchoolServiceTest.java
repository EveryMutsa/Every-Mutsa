package com.example.everymutsa.web.school.service;

import static com.example.everymutsa.common.fixture.SchoolFixture.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.everymutsa.web.school.domain.dto.SchoolRegister;
import com.example.everymutsa.web.school.domain.dto.SchoolResponse;
import com.example.everymutsa.web.school.domain.dto.SchoolUpdate;

@SpringBootTest
class SchoolServiceTest {

	@Autowired
	SchoolService schoolService;

	@Test
	@DisplayName("학교를 등록할 수 있다.")
	void registerSchoolTest() throws Exception {
		//given
		SchoolRegister schoolRegister = createSchoolRegister();
		//when
		Long registeredSchoolId = schoolService.save(schoolRegister);
		SchoolResponse schoolResponse = schoolService.readOne(registeredSchoolId);
		//then
		Assertions.assertThat(schoolResponse.getId()).isEqualTo(registeredSchoolId);
	}

	@Test
	@DisplayName("상태바에 대한 정보를 DB에 업데이트 할 수 있다.")
	void updateStatus() throws Exception {
		//given
		SchoolUpdate schoolUpdate = createSchoolUpdate();
		Long id = schoolService.save(createSchoolRegister());
		//when
		schoolService.updateStatus(id, schoolUpdate);
		//then
		Assertions.assertThatNoException();
	}

}