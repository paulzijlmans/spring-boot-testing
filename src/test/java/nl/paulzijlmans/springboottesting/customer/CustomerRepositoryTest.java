package nl.paulzijlmans.springboottesting.customer;

import static org.junit.jupiter.api.Assertions.*;

import java.time.ZonedDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
class CustomerRepositoryTest {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private TestEntityManager testEntityManager;

  @Test
  void shouldSaveAndRetrieveJpaEntity() {
    Customer customer = new Customer();
    customer.setName("Peter");
    customer.setJoinedAt(ZonedDateTime.now());

    Customer result = testEntityManager.persistFlushFind(customer);

    assertNotNull(result.getId());
  }

  @Test
  void shouldReturnCustomerThatJoinedTheEarliest() {
    customerRepository.deleteAll();

    Customer customerOne = new Customer("duke", ZonedDateTime.now());
    Customer customerTwo = new Customer("anna", ZonedDateTime.now().minusMinutes(42));
    Customer customerThree = new Customer("mike", ZonedDateTime.now().minusDays(42));

    customerRepository.saveAll(List.of(customerOne, customerTwo, customerThree));

    Customer result = customerRepository.getEarlyBird();

    assertEquals("mike", result.getName());
  }
}