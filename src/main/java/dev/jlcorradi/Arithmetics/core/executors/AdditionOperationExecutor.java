package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import org.springframework.stereotype.Component;

import static dev.jlcorradi.Arithmetics.core.executors.OperationExecutionUtils.extractDouble;

@Component
@SupportsOperation(operation = OperationType.ADDITION, inputCount = 2)
public class AdditionOperationExecutor implements OperationExecutor {
  @Override
  public Object execute(Object[] input) {
    return extractDouble(input[0]) + extractDouble(input[1]);
  }
}
