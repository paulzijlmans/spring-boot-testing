package nl.paulzijlmans.springboottesting.pricing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {

  @Mock
  private ProductVerifier mockedProductVerifier;

  @InjectMocks
  private PricingService service;

  @Test
  void shouldReturnCheapPriceWhenProductIsInStockOfCompetitor() {
    Mockito.when(mockedProductVerifier.isCurrentlyInStockOfCompetitor("AirPods"))
        .thenReturn(true);

    assertEquals(new BigDecimal("99.99"), service.calculatePrice("AirPods"));
  }

  @Test
  void shouldReturnHigherPriceWhenProductIsNotInStockOfCompetitor() {
    Mockito.when(mockedProductVerifier.isCurrentlyInStockOfCompetitor("AirPods"))
        .thenReturn(false);

    assertEquals(new BigDecimal("149.99"), service.calculatePrice("AirPods"));
  }
}