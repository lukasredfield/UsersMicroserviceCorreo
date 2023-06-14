package com.correoargentino.services.user.domain.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class Preferences implements Serializable {
  private String setting;
}
