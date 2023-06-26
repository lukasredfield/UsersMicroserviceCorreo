package com.correoargentino.services.user.application.messaging;

public interface CommandHandler<T extends Command> extends MessageHandler<T> {
  public void handle(T command);
}
