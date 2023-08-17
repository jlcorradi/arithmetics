package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import org.springframework.stereotype.Component;

import static dev.jlcorradi.Arithmetics.core.executors.OperationExecutionUtils.extractDouble;

@Component
@SupportsOperation(operation = OperationType.SQUARE_ROOT, inputCount = 1)
public class SquareRootOperationExecutor implements OperationExecutor {
  @Override
  public Object execute(Object[] input) {
    return Math.sqrt(extractDouble(input[0]));
  }
}
