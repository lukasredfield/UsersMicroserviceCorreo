package com.correoargentino.services.user.application.messaging;

public non-sealed interface CommandHandler<T extends Command<R>, R> extends MessageHandler<T, R> {
  public R handle(T command);
}
