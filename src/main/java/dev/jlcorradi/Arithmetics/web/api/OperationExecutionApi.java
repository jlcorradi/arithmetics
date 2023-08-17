package dev.jlcorradi.Arithmetics.web.api;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.executors.OperationExecutionManager;
import dev.jlcorradi.Arithmetics.web.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(Paths.OPERATION_V1)
public class OperationExecutionApi {

  private final OperationExecutionManager operationExecutionManager;

  record OperationRequest(
      OperationType type,
      Object[] params
  ) {
  }

  record OperationResponse(
      Object result
  ) {
  }

  @PostMapping
  public ResponseEntity<OperationResponse> executeOperation(@RequestBody OperationRequest request) {
    OperationResponse result = new OperationResponse(operationExecutionManager.execute(request.type, request.params));
    return ResponseEntity.ok(result);
  }

}
