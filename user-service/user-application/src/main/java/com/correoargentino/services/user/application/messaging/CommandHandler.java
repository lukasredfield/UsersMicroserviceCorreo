package com.correoargentino.services.user.application.messaging;

public interface CommandHandler<T extends Command> extends MessageHandler<T, Void> {
  public Void handle(T command);
}
