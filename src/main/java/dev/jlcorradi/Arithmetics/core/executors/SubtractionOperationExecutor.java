package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import org.springframework.stereotype.Component;

import static dev.jlcorradi.Arithmetics.core.executors.OperationExecutionUtils.extractDouble;

@SupportsOperation(operation = OperationType.SUBTRACTION, inputCount = 2)
@Component
public class SubtractionOperationExecutor implements OperationExecutor {
  @Override
  public Object execute(Object[] input) {
    return extractDouble(input[0]) - extractDouble(input[1]);
  }
}
