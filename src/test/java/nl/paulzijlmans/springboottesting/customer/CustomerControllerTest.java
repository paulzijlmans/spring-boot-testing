package nl.paulzijlmans.springboottesting.customer;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import nl.paulzijlmans.springboottesting.WebSecurityConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@Import(WebSecurityConfig.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

  @Autowired
  MockMvc mockMvc;

  @MockBean
  CustomerService customerService;

  private static final Customer customer = new Customer();

  @BeforeAll
  public static void init() {
    customer.setId(3L);
    customer.setName("John");
    customer.setJoinedAt(ZonedDateTime.of(2022, 6, 23, 0, 0, 0, 0, ZoneId.of("Europe/Amsterdam")));
  }

  @Test
  void shouldReturn200() throws Exception {
    this.mockMvc
        .perform(get("/api/customers"))
        .andExpect(status().isOk());
  }

  @Test
  void shouldReturnListOfCustomers() throws Exception {
    when(customerService.getAllCustomers()).thenReturn(List.of(customer));

    this.mockMvc
        .perform(get("/api/customers"))
        .andExpect(jsonPath("$.size()").value(1))
        .andExpect(jsonPath("$[0].id").value(3))
        .andExpect(jsonPath("$[0].name").value("John"))
        .andExpect(jsonPath("$[0].joinedAt").value("2022-06-23T00:00:00+02:00"));
  }

  @Test
  void shouldForbidAnonymousUsersFetchingCustomersById() throws Exception {
    this.mockMvc
        .perform(get("/api/customers/3"))
        .andExpect(status().isUnauthorized());
  }

  @Test
  @WithMockUser
  void shouldAllowAuthenticatedUsersToFetchCustomersById() throws Exception {
    Mockito.when(customerService.getCustomerById(3L))
        .thenReturn(customer);

    this.mockMvc
        .perform(get("/api/customers/3"))
        .andExpect(jsonPath("$.id").value(3))
        .andExpect(jsonPath("$.name").value("John"))
        .andExpect(jsonPath("$.joinedAt").value("2022-06-23T00:00:00+02:00"));
  }
}