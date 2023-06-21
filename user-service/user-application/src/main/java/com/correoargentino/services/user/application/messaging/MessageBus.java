package com.correoargentino.services.user.application.messaging;

import jakarta.validation.Valid;

public interface MessageBus {
  public <T extends Message<Void>> void dispatch(@Valid T request);

  public <T extends Message<R>, R> R request(@Valid T request);
}
