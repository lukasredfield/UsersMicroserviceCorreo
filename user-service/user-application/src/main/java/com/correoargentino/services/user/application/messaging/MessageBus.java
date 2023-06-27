package com.correoargentino.services.user.application.messaging;

import jakarta.validation.Valid;

public interface MessageBus {
  public <T extends Command> void dispatch(@Valid T request);

  public <T extends Query<R>, R> R request(@Valid T request);
}
