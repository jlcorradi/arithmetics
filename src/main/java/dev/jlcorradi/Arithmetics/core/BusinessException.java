package dev.jlcorradi.Arithmetics.core;

public class BusinessException extends RuntimeException {

  public BusinessException() {
    super(MessageConstants.GENERIC_EXECUTION_ERR);
  }

  public BusinessException(String message) {
    super(message);
  }
}
