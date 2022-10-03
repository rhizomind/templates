package com.rhizomind.quickapp;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = {Application.class})
@ActiveProfiles("integration")
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
