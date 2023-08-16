package dev.jlcorradi.Arithmetics.core.base;

import dev.jlcorradi.Arithmetics.core.model.RecordStatus;

public interface SoftDeletable {
  void setStatus(RecordStatus status);
}
