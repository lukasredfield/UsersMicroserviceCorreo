package com.correoargentino.services.user.presentation.filter;

import com.correoargentino.services.user.presentation.exception.AuthorizationException;
import com.correoargentino.services.user.presentation.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;

/**
 * Filtro para manejar las excepciones de autorización.
 * Esta clase extiende OncePerRequestFilter de Spring para asegurarse de que el filtro se aplique una vez por cada solicitud.
 * Utiliza la anotación @RequiredArgsConstructor de Lombok para generar un constructor con argumentos para el campo 'objectMapper'.
 * Implementa el método doFilterInternal() para realizar el filtrado y el manejo de excepciones de autorización.
 * En caso de producirse una excepción de autorización, se genera una respuesta de error con el mensaje de error correspondiente.
 */
@RequiredArgsConstructor
public class AuthorizationExceptionFilter extends OncePerRequestFilter {
  private final ObjectMapper objectMapper;


  /**
   * Método para realizar el filtrado y el manejo de excepciones de autorización.
   * @param request     La solicitud HTTP recibida.
   * @param response    La respuesta HTTP a enviar.
   * @param filterChain El objeto FilterChain para invocar el siguiente filtro en la cadena.
   * @throws ServletException Si ocurre una excepción en el servlet.
   * @throws IOException      Si ocurre una excepción de entrada/salida.
   */
  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (AuthorizationException exception) {
      var errorResponse = new ErrorResponse("Unauthorized", exception.getMessage(),
              HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now(),
              URI.create(ServletUriComponentsBuilder.
                      fromCurrentRequestUri().toUriString()));

      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      response.getWriter().write(convertObjectToJson(errorResponse));
    }
  }

  /**
   * Método para convertir un objeto a formato JSON.
   * @param object El objeto a convertir.
   * @return La representación en formato JSON del objeto.
   * @throws JsonProcessingException Si ocurre una excepción al procesar el objeto JSON.
   */

  private String convertObjectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    return objectMapper.writeValueAsString(object);
  }
}
