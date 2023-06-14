package com.correoargentino.services.user.application.messaging;

public interface MessageBus {
  public <T extends Message<Void>> void dispatch(T request);

  public <T extends Message<R>, R> R request(T request);
}
