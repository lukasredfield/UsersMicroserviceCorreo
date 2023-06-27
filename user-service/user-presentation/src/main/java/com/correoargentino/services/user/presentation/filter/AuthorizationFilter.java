package com.correoargentino.services.user.presentation.filter;

import com.correoargentino.services.user.presentation.exception.AuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro de autorización para verificar y validar los tokens de autorización en las solicitudes.
 * Esta clase extiende OncePerRequestFilter de Spring para asegurarse de que el filtro se aplique una vez por cada solicitud.
 * Define una lista de patrones de URL que se excluyen del filtrado.
 * Utiliza la anotación @Value de Spring para inyectar el valor de la propiedad 'security.jwt.token.secret-key' desde la configuración.
 * Implementa el método doFilterInternal() para realizar el filtrado y validar los tokens de autorización.
 * Utiliza la biblioteca JJWT para parsear y verificar los tokens de autorización.
 * Si el token no es válido o está ausente, se lanza una AuthorizationException.
 * Si el token es válido, se extraen las reclamaciones del token y se establecen como atributo en la solicitud.
 * Utiliza el método shouldNotFilter() para determinar si el filtro debe aplicarse o no según la ruta de la solicitud.
 */

public class AuthorizationFilter extends OncePerRequestFilter {
  private static final List<String> excludeUrlPatterns = List.of("/actuator/*", "/v1/users");

  @Value("${security.jwt.token.secret-key}")
  private String secret;

  /**
   * Método para realizar el filtrado y validar los tokens de autorización.
   * @param request      La solicitud HTTP recibida.
   * @param response     La respuesta HTTP a enviar.
   * @param filterChain  El objeto FilterChain para invocar el siguiente filtro en la cadena.
   * @throws ServletException Si ocurre una excepción en el servlet.
   * @throws IOException      Si ocurre una excepción de entrada/salida.
   */
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      throw new AuthorizationException();
    }

    final String token = authHeader.substring(7);
    Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    request.setAttribute("claims", claims);

    filterChain.doFilter(request, response);
  }

  /**
   * Método para determinar si el filtro debe aplicarse o no según la ruta de la solicitud.
   * @param request La solicitud HTTP recibida.
   * @return true si el filtro no debe aplicarse, false si el filtro debe aplicarse.
   * @throws ServletException Si ocurre una excepción en el servlet.
   */
  @Override
  protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
    AntPathMatcher pathMatcher = new AntPathMatcher();

    return excludeUrlPatterns.stream()
            .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
  }
}
