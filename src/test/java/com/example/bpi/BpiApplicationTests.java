package com.example.bpi;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.bpi.controller.TestController;
import com.example.bpi.service.impl.BpiServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BpiApplicationTests {

	@Autowired
	TestController testController;
	@Test
	void contextLoads() {
		assertThat(testController).isNotNull();
	}

}
