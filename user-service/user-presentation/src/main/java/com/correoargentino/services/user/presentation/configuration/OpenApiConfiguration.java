package com.correoargentino.services.user.presentation.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .components(new Components())
        .info(new Info()
            .title("User Service API")
            .description("""
                An invoice represents a balance owed to you by a Customer. Each invoice has a collection 
                of line items that detail the products or services that are now due. An invoice can be in one 
                of many different states depending on the due date, payments, and whether it was sent to 
                or viewed by the customer.""")
            .version(getClass().getPackage().getImplementationVersion())
        );
  }
}
