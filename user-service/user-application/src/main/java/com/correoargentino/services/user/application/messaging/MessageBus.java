package com.correoargentino.services.user.application.messaging;

public interface MessageBus {
  public <T extends Message<R>, R> R dispatch(T request);
}
