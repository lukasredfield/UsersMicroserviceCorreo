package com.correoargentino.services.user.application.messaging;

public sealed interface Message<R> permits Command, Query {
}
