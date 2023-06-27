package com.correoargentino.services.user.infrastructure.query.mapper;

import com.correoargentino.services.user.application.query.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.SneakyThrows;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {
  @Override
  @SneakyThrows
  public User mapRow(ResultSet rs, int rowNum) {
    return new User(
        UUID.fromString(rs.getString("id")),
        rs.getString("username"),
        rs.getString("first_name"),
        rs.getString("last_name"),
        rs.getString("email_address"),
        rs.getString("phone_number"),
        convertToMap(rs.getString("parameters")),
        rs.getString("status"),
        rs.getTimestamp("created_at").toLocalDateTime(),
        rs.getTimestamp("updated_at").toLocalDateTime());
  }

  private Map<String, Object> convertToMap(String json) throws JsonProcessingException {
    var objectMapper = new ObjectMapper();
    TypeReference<HashMap<String, Object>> typeRef = new TypeReference<>() {};
    return objectMapper.readValue(json, typeRef);
  }
}
