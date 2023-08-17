package dev.jlcorradi.Arithmetics.web;

import lombok.Getter;

public enum HeaderMessageType {
  SUCCESS("x-success-message"),
  ERROR("x-error-message");

  @Getter
  private final String headerName;

  HeaderMessageType(String headerName) {
    this.headerName = headerName;
  }

}
