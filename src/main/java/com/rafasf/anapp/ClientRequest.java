package com.rafasf.anapp;

import io.vavr.control.Either;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.function.Supplier;

public class ClientRequest {
  public static <T> Either<AnError, T> clientRequest(Supplier<T> request) {
    try {
      return Either.right(request.get());
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      return Either.left(new AnError(e.getStatusCode(), e.getMessage()));
    }
  }
}
