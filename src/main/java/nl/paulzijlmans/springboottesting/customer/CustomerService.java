package nl.paulzijlmans.springboottesting.customer;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  public List<Customer> getAllCustomers() {
    return customerRepository.findAll();
  }

  public Customer getCustomerById(Long id) {
    return customerRepository
        .findById(id)
        .orElseThrow(CustomerNotFoundException::new);
  }
}
