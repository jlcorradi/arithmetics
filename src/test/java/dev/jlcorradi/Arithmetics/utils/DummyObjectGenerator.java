package dev.jlcorradi.Arithmetics.utils;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.model.Operation;
import dev.jlcorradi.Arithmetics.core.webclient.randomdotnet.RandomDotNetGenerateRandomStringResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DummyObjectGenerator {
  public static RandomDotNetGenerateRandomStringResponse randomStringResponse(
      FluentEditor<RandomDotNetGenerateRandomStringResponse.Random.RandomBuilder> editor) {
    RandomDotNetGenerateRandomStringResponse.Random.RandomBuilder resultBuilder =
        RandomDotNetGenerateRandomStringResponse.Random.builder()
            .data(new String[]{"randomlygeneratedtext"});

    RandomDotNetGenerateRandomStringResponse.RandomDotNetGenerateRandomStringResponseBuilder builder =
        RandomDotNetGenerateRandomStringResponse.builder()
            .result(RandomDotNetGenerateRandomStringResponse.Result.builder()
                .random(
                    editor.edit(resultBuilder)
                        .build()
                )
                .build());

    return builder.build();
  }

  public static ArithmeticsUser user(FluentEditor<ArithmeticsUser.ArithmeticsUserBuilder> editor) {
    ArithmeticsUser.ArithmeticsUserBuilder builder = ArithmeticsUser.builder()
        .id(1L)
        .email("testuser@jlcorradi.dev")
        .password("password")
        .status(RecordStatus.ACTIVE)
        .userBalance(BigDecimal.TEN);

    return editor.edit(builder)
        .build();
  }

  public static Operation operation(FluentEditor<Operation.OperationBuilder> editor) {
    Operation.OperationBuilder builder = Operation.builder()
        .cost(BigDecimal.ONE)
        .type(OperationType.ADDITION)
        .status(RecordStatus.ACTIVE)
        .id(1L);

    return editor.edit(builder)
        .build();
  }

}
