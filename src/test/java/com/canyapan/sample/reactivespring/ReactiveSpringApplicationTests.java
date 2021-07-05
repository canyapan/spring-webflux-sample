package com.canyapan.sample.reactivespring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReactiveSpringApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void shouldStartApp() {
		ReactiveSpringApplication.main(new String[] {"--spring.profiles.active=test", "--server.port=0"});
	}

}
