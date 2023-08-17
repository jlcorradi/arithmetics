package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.BusinessException;
import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
@SupportsOperation(operation = OperationType.DIVISION, inputCount = 2)
public class DivisionOperationExecutor implements OperationExecutor {

  @Override
  public Object execute(Object[] input) {
    int decimalPlaces = input.length == 2 ? 2 : (Integer) input[2];
    DecimalFormat decimalFormat = new DecimalFormat("#." + "0".repeat(decimalPlaces));
    double result = (Double) input[0] / (Double) input[1];
    String roundedNumber = decimalFormat.format(result);
    return Double.valueOf(roundedNumber);
  }

  @Override
  public void validateInput(Object[] input) {
    boolean validInputLength = input.length == 2 || input.length == 3;
    if (!validInputLength) {
      throw new BusinessException(MessageConstants.WRONG_INPUT_COUNT_ERR);
    }
    if (Double.valueOf(0f).equals(input[1])) {
      throw new BusinessException(MessageConstants.DIVISION_BY_ZERO_ERR);
    }
  }
}
