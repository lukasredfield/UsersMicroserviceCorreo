package com.correoargentino.services.user.infrastructure.persistence;

import com.correoargentino.services.user.application.port.output.UserReadRepository;
import com.correoargentino.services.user.application.query.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReadRepositoryImpl implements UserReadRepository {
  private final JdbcTemplate jdbcTemplate;
  private final ObjectMapper objectMapper;

  @Override
  public Optional<User> findById(UUID id) {
    var sql = """
        SELECT * FROM users s WHERE s.id = ? AND s.deleted_at IS NULL
        """;

    return jdbcTemplate.query(sql, (rs, rowNum) ->
            new User(
                UUID.fromString(rs.getString("id")),
                rs.getString("username"),
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email_address"),
                rs.getString("phone_number"),
                convertToJsonNode(rs.getString("preferences")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime())
        , id).stream().findFirst();
  }

  private JsonNode convertToJsonNode(String json) {
    try {
      return objectMapper.readTree(json);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}






