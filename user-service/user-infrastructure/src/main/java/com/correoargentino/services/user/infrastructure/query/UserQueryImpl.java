package com.correoargentino.services.user.infrastructure.query;

import com.correoargentino.services.user.application.port.output.UserQuery;
import com.correoargentino.services.user.application.query.model.User;
import com.correoargentino.services.user.infrastructure.query.mapper.UserMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserQueryImpl implements UserQuery {
  private final JdbcTemplate jdbcTemplate;

  @Override
  public Optional<User> getById(UUID id) {
    var sql = """
        SELECT * FROM users s WHERE s.id = ? AND s.deleted_at IS NULL
        """;

    return jdbcTemplate.query(sql, new UserMapper(), id).stream().findFirst();
  }

  @Override
  public Optional<User> getByUsername(String username) {
    var sql = """
        SELECT * FROM users s WHERE s.username = ? AND s.deleted_at IS NULL
        """;

    return jdbcTemplate.query(sql, new UserMapper(), username).stream().findFirst();
  }

  @Override
  public Optional<User> getByEmailAddress(String emailAddress) {
    var sql = """
        SELECT * FROM users s WHERE s.email_address = ? AND s.deleted_at IS NULL
        """;

    return jdbcTemplate.query(sql, new UserMapper(), emailAddress).stream().findFirst();
  }

  @Override
  public List<User> findUsers() {
    var sql = """
        SELECT * FROM users s WHERE s.deleted_at IS NULL
        """;

    return jdbcTemplate.query(sql, new UserMapper());
  }
}






