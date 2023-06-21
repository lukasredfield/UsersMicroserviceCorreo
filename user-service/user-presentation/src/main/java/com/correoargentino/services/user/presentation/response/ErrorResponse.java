package com.correoargentino.services.user.presentation.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.lang.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResponse(
    @Nullable
    String title,
    String detail,
    @Nullable
    Map<String, Object> parameters,
    int status,
    LocalDateTime timestamp,
    @Nullable
    URI instance) {

  public ErrorResponse(String title, String detail,
                       int status, LocalDateTime timestamp, URI instance) {
    this(title, detail, null, status, timestamp, instance);
  }
}
