package com.coding.logfilter;

import com.coding.logfilter.model.LogEvent;
import com.coding.logfilter.repository.LogEventRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(args="logFile.txt")
class LogFilterApplicationTests {

	@Autowired
	private LogEventRepository logEventRepository;

	@Test
	void contextLoads() {
	}

	@Test
	void verifyValidInputs() {
		assertThat(logEventRepository.findAll()).isInstanceOf(List.class);
		assertEquals(logEventRepository.count() , 3);

		Optional<LogEvent> logEventOptional = logEventRepository.findById("scsmbstgra");
		assertTrue(logEventOptional.isPresent());
		assertTrue(logEventOptional.get().isAlert());

		assertTrue(logEventRepository.existsById("scsmbstgrb"));

		assertTrue(logEventRepository.existsById("scsmbstgrc"));
	}
}
