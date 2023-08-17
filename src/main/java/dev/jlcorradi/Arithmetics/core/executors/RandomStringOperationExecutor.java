package dev.jlcorradi.Arithmetics.core.executors;

import dev.jlcorradi.Arithmetics.core.BusinessException;
import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.webclient.randomdotnet.RandomDotNetGenerateRandomStringRequest;
import dev.jlcorradi.Arithmetics.core.webclient.randomdotnet.RandomOrgClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

import static dev.jlcorradi.Arithmetics.core.executors.OperationExecutionUtils.extractInt;

@RequiredArgsConstructor
@Component
@SupportsOperation(operation = OperationType.RANDOM_STRING, inputCount = 1)
public class RandomStringOperationExecutor implements OperationExecutor {

  public static final String RANDOM_DOT_ORG_VERSIION = "2.0";
  public static final String RANDOM_DOT_ORG_METHOD = "generateStrings";
  public static final String API_KEY_PARAM = "apiKey";
  public static final String NUMBER_OF_LINES_PARAM = "n";
  public static final String LENGTH_PARAM = "length";
  public static final String CHARACTERS_PARAM = "characters";
  public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789[]{}()|?%$#@*";

  private final RandomOrgClient randomOrgClient;

  @Value("${randomDotOrg.apiKey}")
  private String randomDotOrgApiKey;

  @Override
  public Object execute(Object[] input) {
    RandomDotNetGenerateRandomStringRequest request = new RandomDotNetGenerateRandomStringRequest(
        RANDOM_DOT_ORG_VERSIION,
        RANDOM_DOT_ORG_METHOD,
        Map.of(API_KEY_PARAM, Optional.ofNullable(randomDotOrgApiKey).orElse(""),
            NUMBER_OF_LINES_PARAM, 1,
            LENGTH_PARAM, extractInt(input[0]),
            CHARACTERS_PARAM, ALPHABET),
        1L
    );

    try {
      return randomOrgClient.generateRandomString(request)
          .getGeneratedString();
    } catch (Exception ex) {
      throw new BusinessException(MessageConstants.RETRIEVING_RANDOM_STRING_ERR);
    }

  }

  @Override
  public void validateInput(Object[] input) {
    OperationExecutor.super.validateInput(input);
    if (extractInt(input[0]) > 32) {
      throw new BusinessException(MessageConstants.INVALID_LENGTH_ERR);
    }
  }
}
