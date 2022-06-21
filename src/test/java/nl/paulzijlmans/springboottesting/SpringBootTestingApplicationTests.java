package nl.paulzijlmans.springboottesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringBootTestingApplicationTests {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	void contextLoads() {
		ResponseEntity<Object> actuatorResult =
				this.testRestTemplate.getForEntity("/actuator/health", Object.class);

		assertEquals(HttpStatus.OK, actuatorResult.getStatusCode());
	}

}
