package com.correoargentino.services.user.presentation.configuration;

import com.correoargentino.services.user.presentation.filter.AuthorizationFilter;
import com.correoargentino.services.user.presentation.filter.AuthorizationExceptionFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {
  private final ObjectMapper objectMapper;
  @Bean
  public FilterRegistrationBean<AuthorizationExceptionFilter> authorizationExceptionFilter() {
    var filter = new FilterRegistrationBean<AuthorizationExceptionFilter>();
    filter.setFilter(new AuthorizationExceptionFilter(objectMapper));
    filter.addUrlPatterns("/v1/users/*");
    return filter;
  }
  @Bean
  public FilterRegistrationBean<AuthorizationFilter> authorizationFilter() {
    var filter = new FilterRegistrationBean<AuthorizationFilter>();
    filter.setFilter(new AuthorizationFilter());
    filter.addUrlPatterns("/v1/users/*");
    return filter;
  }
}