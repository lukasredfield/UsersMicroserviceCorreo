package com.correoargentino.services.user.infrastructure.messaging;

import com.correoargentino.services.user.application.messaging.Message;
import com.correoargentino.services.user.application.messaging.MessageBus;
import com.correoargentino.services.user.application.messaging.MessageHandler;
import com.correoargentino.services.user.application.messaging.exception.HandlerNotFoundException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.validation.Valid;
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
  private final ConcurrentMap<Class<?>, MessageHandler<?, ?>> handlers =
      new ConcurrentHashMap<>();

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Message<Void>> void dispatch(T request) {
    var handler = (MessageHandler<T, Void>) handlers.get(request.getClass());
    if (handler == null) {
      throw new HandlerNotFoundException(
          String.format("%s handler was not found!", request.getClass().getSimpleName()));
    }

    handler.handle(request);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T extends Message<R>, R> R request(T request) {
    var handler = (MessageHandler<T, R>) handlers.get(request.getClass());
    if (handler == null) {
      throw new HandlerNotFoundException(
          String.format("%s handler was not found!", request.getClass().getSimpleName()));
    }

    return handler.handle(request);
  }

  @PostConstruct
  private void registerHandlers() {
    log.info("Registering handlers into the message handler map.");
    applicationContext.getBeansOfType(MessageHandler.class)
        .values().forEach((bean -> handlers.put(resolveTypeArguments(bean.getClass()), bean)));
  }

  @PreDestroy
  private void unregisterHandlers() {
    log.info("Clearing message handler map.");
    handlers.clear();
  }

  private Class<?> resolveTypeArguments(Class<?> handlerClass) {
    Class<?>[] arguments = GenericTypeResolver
        .resolveTypeArguments(handlerClass, MessageHandler.class);
    return (arguments != null) ? arguments[0] : null;
  }
}