package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import org.springframework.stereotype.Component;

import static dev.jlcorradi.Arithmetics.core.executors.OperationExecutionUtils.extractDouble;

@Component
@SupportsOperation(operation = OperationType.MULTIPLICATION, inputCount = 2)
public class MultiplicationOperationExecutor implements OperationExecutor {

  @Override
  public Object execute(Object[] input) {
    return extractDouble(input[0]) * extractDouble(input[1]);
  }
}
