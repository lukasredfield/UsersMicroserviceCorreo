package com.correoargentino.services.user.presentation.filter;

import com.correoargentino.services.user.presentation.exception.AuthorizationException;
import com.correoargentino.services.user.presentation.response.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RequiredArgsConstructor
public class AuthorizationExceptionFilter extends OncePerRequestFilter {
  private final ObjectMapper objectMapper;

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

  private String convertObjectToJson(Object object) throws JsonProcessingException {
    if (object == null) {
      return null;
    }
    return objectMapper.writeValueAsString(object);
  }
}
