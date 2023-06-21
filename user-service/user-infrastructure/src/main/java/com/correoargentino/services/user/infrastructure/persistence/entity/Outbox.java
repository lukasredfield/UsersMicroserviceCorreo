package com.correoargentino.services.user.infrastructure.persistence.entity;

import com.correoargentino.services.user.infrastructure.persistence.converter.JsonNodeAttributeConverter;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Builder
@Entity
@Table(name = "outbox")
public class Outbox {
  @Id
  @UuidGenerator(style = UuidGenerator.Style.RANDOM)
  @GeneratedValue
  private UUID id;

  @Column(nullable = false)
  private String aggregateType;

  @Column(nullable = false)
  private UUID aggregateId;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  @JdbcTypeCode(SqlTypes.JSON)
  @Convert(converter = JsonNodeAttributeConverter.class)
  private JsonNode payload;

  @Column(nullable = false)
  private LocalDateTime timestamp;
}
