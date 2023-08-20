package dev.jlcorradi.Arithmetics.core.commons;

import lombok.Getter;

public enum OperationType {
  ADDITION("Addition"),
  SUBTRACTION("Subtraction"),
  MULTIPLICATION("Multiplication"),
  DIVISION("Division"),
  SQUARE_ROOT("Square Root"),
  RANDOM_STRING("Random String");

  @Getter
  final String description;

  OperationType(String description) {
    this.description = description;
  }
}
