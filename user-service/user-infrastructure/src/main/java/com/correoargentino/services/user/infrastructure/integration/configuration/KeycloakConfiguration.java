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
  @Value("${keycloak.host}")
  private String host;

  @Value("${keycloak.client-id}")
  private String clientId;

  @Value("${keycloak.client-secret}")
  private String clientSecret;

  @Value("${keycloak.realm}")
  private String realm;

  @Bean
  public Keycloak keycloak() {
    return KeycloakBuilder.builder()
        .serverUrl(host)
        .clientId(clientId)
        .clientSecret(clientSecret)
        .grantType(OAuth2Constants.PASSWORD).username("lucas").password("admin")
        .realm(realm)
        .build();
  }

  @Bean
  public UsersResource realmResource(Keycloak keycloak) {
    return keycloak.realm(realm).users();
  }
}

/**/

