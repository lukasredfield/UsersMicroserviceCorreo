package com.correoargentino.services.user.application.messaging;

public interface QueryHandler<T extends Query<R>, R> extends MessageHandler<T> {
  public R handle(T query);
}
