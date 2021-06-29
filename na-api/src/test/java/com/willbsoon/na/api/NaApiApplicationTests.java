package com.willbsoon.na.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class NaApiApplicationTests {
	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Test
	void profileTest() {
		System.out.println("================================================");
		System.out.println(activeProfile);
	}

	@Test
	void contextLoads() {
	}

}
