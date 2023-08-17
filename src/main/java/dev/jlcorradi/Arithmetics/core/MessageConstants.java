package dev.jlcorradi.Arithmetics.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageConstants {
  public static final String ACCESS_DENIED_ERR = "Access Denied";

  public static final String RESOURCE_NOT_FOUND_ERR = "Resource not found";
  public static final String WRONG_INPUT_COUNT_ERR = "Wrong number of parameters";
  public static final String UNSUPPORTED_OPERATION_ERR = "Unsupported Operation";
  public static final String DIVISION_BY_ZERO_ERR = "Division By Zero";
  public static final String RETRIEVING_RANDOM_STRING_ERR = "Error Retrieving Random String";
  public static final String INVALID_LENGTH_ERR = "Invalid Length";
  public static final String GENERIC_EXECUTION_ERR = "An error occurred while trying to execute the operation. Make sure the parameters are correct";
}
