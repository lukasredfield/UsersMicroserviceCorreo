package com.correoargentino.services.user.infrastructure.integration.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {
  @Value("http://localhost:9090")
  private String host;

  @Value("user-service")
  private String clientId;

  @Value("OEhHg3NvFDeFORAGFTMtErwDUTvyO6u1")
  private String clientSecret;

  @Value("customers")
  private String realm;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(host)
        .clientId(clientId)
        .clientSecret(clientSecret)
        .grantType(OAuth2Constants.PASSWORD).username("nicolas").password("admin")
        .realm(realm)
        .build();
  }

  @Bean
  public UsersResource realmResource(Keycloak keycloak) {
    return keycloak.realm(realm).users();
  }
}

/**/

