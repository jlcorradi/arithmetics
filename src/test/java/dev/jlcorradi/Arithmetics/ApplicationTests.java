package dev.jlcorradi.Arithmetics;

import dev.jlcorradi.Arithmetics.web.security.TestContextSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestContextSecurityConfig.class)
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}
