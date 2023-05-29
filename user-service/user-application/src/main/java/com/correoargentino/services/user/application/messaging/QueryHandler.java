package com.correoargentino.services.user.application.messaging;

public non-sealed interface QueryHandler<T extends Query<R>, R> extends MessageHandler<T, R> {
  public R handle(T query);
}
