package com.correoargentino.services.user.presentation.configuration;

import com.correoargentino.services.user.presentation.filter.AuthorizationExceptionFilter;
import com.correoargentino.services.user.presentation.filter.AuthorizationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Clase de configuración para la seguridad de la aplicación.
 * <p>
 * Esta clase utiliza la anotación @RequiredArgsConstructor de Lombok para generar un constructor con argumentos para el campo marcado con 'final'.
 * Define dos métodos @Bean que devuelven objetos FilterRegistrationBean para registrar filtros de seguridad en la aplicación.
 * El primer método, authorizationExceptionFilter(), registra un AuthorizationExceptionFilter para manejar excepciones de autorización.
 * El filtro se configura con un ObjectMapper proporcionado en el constructor.
 * Se especifica que el filtro se aplicará a las URL que coincidan con el patrón "/v1/users/*".
 * El segundo método, authorizationFilter(), registra un AuthorizationFilter para realizar la autorización de las solicitudes.
 * No se especifica ninguna configuración adicional para este filtro.
 * También se especifica que este filtro se aplicará a las URL que coincidan con el patrón "/v1/users/*".
 */
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