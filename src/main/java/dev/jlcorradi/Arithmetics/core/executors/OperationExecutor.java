package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.BusinessException;
import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import jakarta.annotation.PostConstruct;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.Optional;

public interface OperationExecutor {

  default void validateInput(Object[] input) {
    SupportsOperation annotation = getOperationMetadata();
    int expectedInputCount = annotation.inputCount();
    if (expectedInputCount != input.length) {
      throw new BusinessException(MessageConstants.WRONG_INPUT_COUNT_ERR);
    }
  }

  Object execute(Object[] input);

  @PostConstruct
  default void validateSupport() {
    if (Optional.ofNullable(
            AnnotationUtils.findAnnotation(getClass(), SupportsOperation.class))
        .isEmpty()
    ) {
      throw new IllegalArgumentException(
          String.format("Operation Executor %s must be annotated with @SupportsOperation", getClass()));
    }
  }

  default OperationType getSupportedOperation() {
    return getOperationMetadata().operation();
  }

  default SupportsOperation getOperationMetadata() {
    SupportsOperation annotation = AnnotationUtils.findAnnotation(getClass(), SupportsOperation.class);
    assert annotation != null;
    return annotation;
  }
}
