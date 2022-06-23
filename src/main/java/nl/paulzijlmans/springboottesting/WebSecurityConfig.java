package nl.paulzijlmans.springboottesting;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.authorizeRequests()
        .mvcMatchers(HttpMethod.GET, "/api/customers").permitAll()
        .mvcMatchers(HttpMethod.GET, "/api/customers/*").authenticated()
        .requestMatchers(EndpointRequest.to(HealthEndpoint.class)).permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic();
    return http.build();
  }
}
