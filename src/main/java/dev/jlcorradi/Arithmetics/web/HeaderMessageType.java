package dev.jlcorradi.Arithmetics.web;

import lombok.Getter;

public enum HeaderMessageType {
  SUCCESS("x-message-success"),
  ERROR("x-message-error");

  @Getter
  private final String headerName;

  HeaderMessageType(String headerName) {
    this.headerName = headerName;
  }

}
