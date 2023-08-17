package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import org.springframework.stereotype.Component;

@SupportsOperation(operation = OperationType.SUBTRACTION, inputCount = 2)
@Component
public class SubtractionOperationExecutor implements OperationExecutor {
  @Override
  public Object execute(Object[] input) {
    return (Double) input[0] - (Double) input[1];
  }
}
