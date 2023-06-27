package com.correoargentino.services.user.infrastructure.integration.configuration;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Clase de configuracion para settear los datos de conexion con keycloak
 */
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

  /**
   * Método que configura y devuelve una instancia de Keycloak.
   * <p>
   * Este método utiliza la anotación @Bean de Spring para indicar que es un bean administrado por Spring.
   * Configura una instancia de KeycloakBuilder utilizando los valores proporcionados y devuelve una instancia de Keycloak.
   *
   * @return Una instancia de Keycloak configurada.
   */
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

  /**
   * Método que crea un bean para obtener el recurso UsersResource de Keycloak para el reino especificado.
   *
   * @param keycloak El cliente Keycloak utilizado para obtener el recurso.
   * @return El recurso UsersResource del reino especificado.
   */
  @Bean
  public UsersResource realmResource(Keycloak keycloak) {
    return keycloak.realm(realm).users();
  }
}

/**/

