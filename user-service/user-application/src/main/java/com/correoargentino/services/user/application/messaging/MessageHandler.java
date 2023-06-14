package com.correoargentino.services.user.application.messaging;

public interface MessageHandler<T extends Message<R>, R> {
  public R handle(T request);
}