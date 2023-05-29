package com.correoargentino.services.user.application.messaging;

public sealed interface MessageHandler<T extends Message<R>, R>
    permits CommandHandler, QueryHandler {
  public R handle(T request);
}