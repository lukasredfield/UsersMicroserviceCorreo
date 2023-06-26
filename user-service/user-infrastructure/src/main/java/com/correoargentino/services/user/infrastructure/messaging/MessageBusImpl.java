package com.correoargentino.services.user.infrastructure.messaging;

import com.correoargentino.services.user.application.messaging.Command;
import com.correoargentino.services.user.application.messaging.CommandHandler;
import com.correoargentino.services.user.application.messaging.MessageBus;
import com.correoargentino.services.user.application.messaging.MessageHandler;
import com.correoargentino.services.user.application.messaging.Query;
import com.correoargentino.services.user.application.messaging.QueryHandler;
import com.correoargentino.services.user.application.messaging.exception.HandlerNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class MessageBusImpl implements MessageBus {
  private final ApplicationContext applicationContext;
  private final ConcurrentMap<Class<?>, MessageHandler<?>> handlers = new ConcurrentHashMap<>();

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Command> void dispatch(T request) {
    var handler = (CommandHandler<T>) getHandler(request.getClass());
    handler.handle(request);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Query<R>, R> R request(T request) {
    var handler = (QueryHandler<T, R>) getHandler(request.getClass());
    return handler.handle(request);
  }

  @PostConstruct
  private void registerHandlers() {
    log.info("Registering handlers into the message handler map.");
    var beans = applicationContext.getBeansOfType(MessageHandler.class);
    beans.values().forEach((bean -> handlers.put(resolveTypeArguments(bean.getClass()), bean)));
  }

  @PreDestroy
  private void unregisterHandlers() {
    log.info("Clearing message handler map.");
    handlers.clear();
  }

  private MessageHandler<?> getHandler(Class<?> handlerClass) {
    var handler = handlers.get(handlerClass);
    if (handler == null) {
      throw new HandlerNotFoundException(
          String.format("%s handler was not found!", handlerClass.getSimpleName()));
    }

    return handler;
  }

  private Class<?> resolveTypeArguments(Class<?> handlerClass) {
    Class<?>[] arguments = GenericTypeResolver
        .resolveTypeArguments(handlerClass, MessageHandler.class);
    return (arguments != null) ? arguments[0] : null;
  }
}