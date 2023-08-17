package dev.jlcorradi.Arithmetics.core.base;

import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;

public interface SoftDeletable {
  void setStatus(RecordStatus status);
}
