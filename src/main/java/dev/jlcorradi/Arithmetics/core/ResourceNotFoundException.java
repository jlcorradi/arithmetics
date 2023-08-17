package dev.jlcorradi.Arithmetics.core;

public class ResourceNotFoundException extends BusinessException {

  public ResourceNotFoundException() {
    super(MessageConstants.RESOURCE_NOT_FOUND_ERR);
  }
}
