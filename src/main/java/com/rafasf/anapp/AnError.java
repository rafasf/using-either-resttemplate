package com.rafasf.anapp;

import lombok.Value;
import org.springframework.http.HttpStatus;

@Value
public class AnError {
  final HttpStatus statusCode;
  final String description;
}
