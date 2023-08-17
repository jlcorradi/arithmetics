package dev.jlcorradi.Arithmetics.core;

public class LowBalanceException extends BusinessException{

  public LowBalanceException() {
    super(MessageConstants.LOW_BALANCE_ERR);
  }
}
