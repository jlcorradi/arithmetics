package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import org.springframework.stereotype.Component;

@Component
@SupportsOperation(operation = OperationType.ADDITION, inputCount = 2)
public class AdditionOperationExecutor implements OperationExecutor {
  @Override
  public Object execute(Object[] input) {
    return (Double) input[0] + (Double) input[1];
  }
}
