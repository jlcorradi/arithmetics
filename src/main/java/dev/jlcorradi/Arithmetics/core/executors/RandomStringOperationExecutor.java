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
  public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

  private final RandomOrgClient randomOrgClient;

  @Value("${randomDotOrg.apiKey}")
  private String randomDotOrgApiKey;

  @Override
  public Object execute(Object[] input) {
    RandomDotNetGenerateRandomStringRequest request = new RandomDotNetGenerateRandomStringRequest(
        RANDOM_DOT_ORG_VERSIION,
        RANDOM_DOT_ORG_METHOD,
        Map.of(API_KEY_PARAM, randomDotOrgApiKey,
            NUMBER_OF_LINES_PARAM, 1,
            LENGTH_PARAM, extractInt(input[0]),
            CHARACTERS_PARAM, ALPHABET),
        1L
    );
    return randomOrgClient.generateRandomString(request)
        .getGeneratedString();

  }

  @Override
  public void validateInput(Object[] input) {
    OperationExecutor.super.validateInput(input);
    if (extractInt(input[0]) > 32) {
      throw new BusinessException(MessageConstants.INVALID_LENGTH_ERR);
    }
  }
}
