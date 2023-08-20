package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.BusinessException;
import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import jdk.jfr.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {OperationExecutionManager.class, AdditionOperationExecutor.class,
    SubtractionOperationExecutor.class, MultiplicationOperationExecutor.class, DivisionOperationExecutor.class,
    SquareRootOperationExecutor.class}
)
class OperationExecutionManagerTest {
  @Autowired
  OperationExecutionManager subject;

  static Stream<Arguments> provideArguments() {
    return Stream.of(
        Arguments.of(OperationType.ADDITION, new Object[]{5D, 6D}, 11D),
        Arguments.of(OperationType.ADDITION, new Object[]{5.23D, 6.22D}, 11.45D),
        Arguments.of(OperationType.SUBTRACTION, new Object[]{10D, 6.5D}, 3.5D),
        Arguments.of(OperationType.MULTIPLICATION, new Object[]{5D, 6D}, 30D),
        Arguments.of(OperationType.DIVISION, new Object[]{149D, 7D}, 21.29D),
        Arguments.of(OperationType.DIVISION, new Object[]{24D, 3D}, 8D),
        Arguments.of(OperationType.SQUARE_ROOT, new Object[]{625D}, 25D)
    );
  }

  @ParameterizedTest
  @MethodSource("provideArguments")
  @Description("Should Execute Operations Successfully")
  void shouldExecuteAdditionSuccess(OperationType operationType, Object[] input, Object expectedResult) {
    // WHEN
    Object result = subject.execute(operationType, input);
    // THEN
    assertEquals(expectedResult, result, String.format("Operation executed: %s", operationType.getDescription()));
  }

  @Test
  @Description("Should Fail Wrong Input Count")
  void shouldFailWrongInputCount() {
    // WHEN
    BusinessException ex = assertThrows(BusinessException.class,
        () -> subject.execute(OperationType.ADDITION, new Object[]{5D})
    );

    // THEN
    assertEquals(MessageConstants.WRONG_INPUT_COUNT_ERR, ex.getMessage());
  }

  @Test
  @Description("Should Fail on Division By Zero")
  void shouldFailOnDivByZero() {
    // WHEN
    final BusinessException businessException = assertThrows(BusinessException.class,
        () -> subject.execute(OperationType.DIVISION, new Object[]{45D, 0D})
    );

    // THEN
    assertEquals(MessageConstants.DIVISION_BY_ZERO_ERR, businessException.getMessage());
  }
}