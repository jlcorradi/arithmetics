package dev.jlcorradi.Arithmetics.web.api;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.executors.OperationExecutionManager;
import dev.jlcorradi.Arithmetics.core.service.OperationService;
import dev.jlcorradi.Arithmetics.web.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(Paths.OPERATIONS_V1)
@RequiredArgsConstructor
public class OperationsApi {

  private final OperationExecutionManager manager;
  private final OperationService operationService;

  public record OperationsResponse(
      OperationType operationType,
      String description,
      int paramsQuantity,
      BigDecimal cost) {
  }

  @GetMapping
  public ResponseEntity<List<OperationsResponse>> listAvailableOperations() {
    List<OperationsResponse> operations = operationService.list().stream()
        .map(operation -> {
          OperationType type = operation.getType();
          return new OperationsResponse(
              type,
              type.getDescription(),
              manager.getMetadata(type).inputCount(),
              operation.getCost()
          );
        })
        .collect(Collectors.toList());
    return ResponseEntity.ok(operations);
  }

}
