package dev.jlcorradi.Arithmetics.web.api;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.model.Record;
import dev.jlcorradi.Arithmetics.core.service.OperationService;
import dev.jlcorradi.Arithmetics.web.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

import static dev.jlcorradi.Arithmetics.web.HttpUtils.getLoggedinUser;

@RequiredArgsConstructor
@RestController
@RequestMapping(Paths.OPERATION_V1)
public class OperationExecutionApi {

  private final OperationService operationService;

  public record OperationRequest(
      OperationType type,
      Object[] params
  ) {
  }

  public record OperationResponse(
      Long id,
      Date date,
      String operationDescription,
      BigDecimal price,
      BigDecimal userBalance,
      String result
  ) {
  }

  @PostMapping
  public ResponseEntity<OperationResponse> executeOperation(@RequestBody OperationRequest request) {
    Record result = operationService.execute(getLoggedinUser(), request.type, request.params);

    return ResponseEntity.ok(
        new OperationResponse(
            result.getId(),
            result.getDate(),
            result.getOperation().getType().getDescription(),
            result.getAmount(),
            result.getUserBalance(),
            result.getOperationResponse()
        )
    );
  }

}
