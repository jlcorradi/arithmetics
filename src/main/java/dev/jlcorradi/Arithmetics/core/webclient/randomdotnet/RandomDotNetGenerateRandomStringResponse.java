package dev.jlcorradi.Arithmetics.core.webclient.randomdotnet;

import dev.jlcorradi.Arithmetics.core.BusinessException;
import dev.jlcorradi.Arithmetics.core.MessageConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class RandomDotNetGenerateRandomStringResponse {
  private String jsonrpc;
  private Result result;
  private Error error;
  private long id;

  public String getGeneratedString() {
    if (Optional.ofNullable(error).isPresent()) {
      throw new BusinessException(MessageConstants.RETRIEVING_RANDOM_STRING_ERR);
    }
    return Optional.ofNullable(result)
        .map(Result::getRandom)
        .map(Random::getData)
        .map(strings -> strings[0])
        .orElseThrow(() -> new BusinessException(MessageConstants.RETRIEVING_RANDOM_STRING_ERR));
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  public static class Result {
    private Random random;
    private long bitsUsed;
    private long bitsLeft;
    private long requestsLeft;
    private long advisoryDelay;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  public static class Random {
    private String[] data;
    private String completionTime;
  }

  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  @Data
  public static class Error {
    private long code;
    private String message;
    private Object data;
  }
}
