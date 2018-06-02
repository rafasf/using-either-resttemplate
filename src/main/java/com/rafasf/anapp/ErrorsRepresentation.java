package com.rafasf.anapp;

import lombok.Value;

import java.util.List;

@Value
public class ErrorsRepresentation {
  List<ErrorRepresentation> errors;

  public static ErrorsRepresentation errorsWith(String description) {
    return new ErrorsRepresentation(
      List.of(new ErrorRepresentation(description))
    );
  }
}
