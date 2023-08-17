package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import org.springframework.stereotype.Component;

@Component
@SupportsOperation(operation = OperationType.MULTIPLICATION, inputCount = 2)
public class MultiplicationOperationExecutor implements OperationExecutor {

  @Override
  public Object execute(Object[] input) {
    return (Double) input[0] * (Double) input[1];
  }
}
