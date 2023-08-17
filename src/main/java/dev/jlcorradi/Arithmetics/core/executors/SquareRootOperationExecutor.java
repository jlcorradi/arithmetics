package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import org.springframework.stereotype.Component;

@Component
@SupportsOperation(operation = OperationType.SQUARE_ROOT, inputCount = 1)
public class SquareRootOperationExecutor implements OperationExecutor {
  @Override
  public Object execute(Object[] input) {
    return Math.sqrt((Double) input[0]);
  }
}
