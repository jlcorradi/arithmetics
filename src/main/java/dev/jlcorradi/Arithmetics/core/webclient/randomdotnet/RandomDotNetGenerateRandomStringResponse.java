package dev.jlcorradi.Arithmetics.core.webclient.randomdotnet;

import dev.jlcorradi.Arithmetics.core.BusinessException;
import dev.jlcorradi.Arithmetics.core.MessageConstants;
import lombok.Data;

import java.util.Optional;

@Data
public class RandomDotNetGenerateRandomStringResponse {
  private String jsonrpc;
  private Result result;
  private long id;

  public String getGeneratedString() {
    return Optional.ofNullable(result)
        .map(Result::getRandom)
        .map(Random::getData)
        .map(strings -> strings[0])
        .orElseThrow(() -> new BusinessException(MessageConstants.RETRIEVING_RANDOM_STRING_ERR));
  }

  @Data
  static class Result {
    private Random random;
    private long bitsUsed;
    private long bitsLeft;
    private long requestsLeft;
    private long advisoryDelay;
  }

  @Data
  static class Random {
    private String[] data;
    private String completionTime;
  }
}
