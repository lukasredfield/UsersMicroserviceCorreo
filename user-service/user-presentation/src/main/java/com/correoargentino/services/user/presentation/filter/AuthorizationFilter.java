package com.correoargentino.services.user.presentation.filter;

import com.correoargentino.services.user.presentation.exception.AuthorizationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

public class AuthorizationFilter extends OncePerRequestFilter {
  private static final List<String> excludeUrlPatterns = List.of("/actuator/*", "/v1/users");

  @Value("${security.jwt.token.secret-key}")
  private String secret;

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

  @Override
  protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
    AntPathMatcher pathMatcher = new AntPathMatcher();

    return excludeUrlPatterns.stream()
        .anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
  }
}
