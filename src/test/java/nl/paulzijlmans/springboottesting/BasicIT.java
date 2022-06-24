package nl.paulzijlmans.springboottesting;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import nl.paulzijlmans.springboottesting.customer.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("integration")
public class BasicIT {

  @Autowired
  private TestRestTemplate testRestTemplate;

  @Test
  void test() {
    System.out.println("... running an integration test with the Maven Failsafe Plugin");
  }

  @Test
  void getCustomers() {
    ResponseEntity<List<Customer>> result =
        this.testRestTemplate
            .exchange(
                "/api/customers",
                HttpMethod.GET,
                new HttpEntity<List<Customer>>(new HttpHeaders()),
                new ParameterizedTypeReference<>() {
                }
            );

    assertEquals(HttpStatus.OK, result.getStatusCode());
  }
}
