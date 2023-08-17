package dev.jlcorradi.Arithmetics.core;

public class InsifficientBalanceException extends BusinessException{

  public InsifficientBalanceException() {
    super(MessageConstants.INSUFFICIENT_BALANCE_ERR);
  }
}
