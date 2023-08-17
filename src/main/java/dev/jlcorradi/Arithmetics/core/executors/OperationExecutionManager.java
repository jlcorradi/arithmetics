package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.BusinessException;
import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class OperationExecutionManager {

  private final Map<OperationType, OperationExecutor> executorMap;

  public OperationExecutionManager(List<OperationExecutor> executors) {
    this.executorMap =
        executors.stream().collect(
            Collectors.toMap(OperationExecutor::getSupportedOperation, operationExecutor -> operationExecutor)
        );
  }

  Object execute(OperationType operationType, Object[] input) {
    return Optional.ofNullable(executorMap.get(operationType))
        .map(operationExecutor -> {
          operationExecutor.validateInput(input);
          return operationExecutor.execute(input);
        })
        .orElseThrow(() -> new BusinessException(MessageConstants.UNSUPPORTED_OPERATION_ERR));
  }
}
