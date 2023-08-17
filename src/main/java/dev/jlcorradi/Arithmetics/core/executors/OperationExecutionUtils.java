package dev.jlcorradi.Arithmetics.core.executors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OperationExecutionUtils {
  public static Double extractDouble(Object obj) {
    return Double.parseDouble(obj.toString());
  }

  public static Integer extractInt(Object obj) {
    String numberAsString = obj.toString();
    if (numberAsString.contains(".")) {
      numberAsString = numberAsString.substring(0, numberAsString.indexOf("."));
    }
    return Integer.parseInt(numberAsString);
  }
}
