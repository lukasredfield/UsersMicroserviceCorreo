package com.correoargentino.services.user.infrastructure.persistence;

import com.correoargentino.services.user.application.port.output.UserReadRepository;
import com.correoargentino.services.user.application.query.model.User;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserReadRepositoryImpl implements UserReadRepository {
  private final JdbcTemplate jdbcTemplate;
  @Override
  public Optional<User> findById(UUID id) {
    try {
      var sql = "SELECT * FROM users u WHERE u.id = ?";
      var result = jdbcTemplate.queryForObject(sql,
          (rs, r) -> new User(UUID.fromString(rs.getString("id"))), id);

      return  Optional.ofNullable(result);
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
