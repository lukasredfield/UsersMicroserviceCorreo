package com.correoargentino.services.user.presentation.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Clase de configuración para OpenAPI.
 *
 * Esta clase utiliza la anotación @Configuration de Spring para indicar que es una clase de configuración.
 * Define un método @Bean que devuelve una instancia personalizada de OpenAPI.
 * La instancia de OpenAPI se configura con los componentes y la información del API.
 * Utiliza la anotación @Bean para indicar que el método debe ser administrado por Spring y que el resultado debe ser registrado como un bean en el contexto de la aplicación.
 */
@Configuration
public class OpenApiConfiguration {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info()
            .title("User Service API")
            .version(getClass().getPackage().getImplementationVersion())
        );
  }
}
