package com.correoargentino.services.user.infrastructure.persistence;

import com.correoargentino.services.user.application.port.output.UserReadRepository;
import com.correoargentino.services.user.application.query.model.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserReadRepositoryImpl implements UserReadRepository {
  private final JdbcTemplate jdbcTemplate;

  @Override
  public Optional<User> findById(UUID id) {
    try {
      var sql = "SELECT * FROM users WHERE id = ?";
      var result = jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
        int userId = rs.getInt("id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String emailAddress = rs.getString("email_address");
        String phoneNumber = rs.getString("phone_number");
        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedAt = rs.getTimestamp("updated_at").toLocalDateTime();

        return new User(userId, firstName, lastName, emailAddress, phoneNumber, createdAt, updatedAt);
      }, id);

      return Optional.ofNullable(result);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }
}






