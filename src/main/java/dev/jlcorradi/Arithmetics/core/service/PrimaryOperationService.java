package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.InsifficientBalanceException;
import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import dev.jlcorradi.Arithmetics.core.executors.OperationExecutionManager;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.model.Operation;
import dev.jlcorradi.Arithmetics.core.model.Record;
import dev.jlcorradi.Arithmetics.core.repository.OperationRepository;
import dev.jlcorradi.Arithmetics.core.repository.RecordRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrimaryOperationService implements OperationService {

  @Getter
  private final OperationRepository repository;

  private final RecordRepository recordRepository;
  private final OperationExecutionManager operationExecutionManager;
  private final ArithmeticsUserService userService;

  @Override
  public Record execute(ArithmeticsUser user, OperationType operationType, Object[] params) {
    Operation operation = repository.findByType(operationType)
        .filter(op -> RecordStatus.ACTIVE.equals(op.getStatus()))
        .orElseThrow(EntityNotFoundException::new);

    BigDecimal userBalance = user.getUserBalance();
    validateUserBalance(userBalance, operation);

    log.debug("Executing Operation {} - User: {}. User Balance: {}", operation, user, userBalance);
    Object result = operationExecutionManager.execute(operationType, params);
    userBalance = userBalance.subtract(operation.getCost());

    log.debug("Operation Executed. New User Balance: {}", userBalance);

    userService.updateBalance(user, userBalance);

    String description = String.format("%s (%s)", operationType.getDescription(),
        Arrays.stream(params)
            .map(String::valueOf)
            .collect(Collectors.joining(", ")));

    Record record = Record.builder()
        .user(user)
        .date(LocalDateTime.now(ZoneOffset.UTC))
        .description(description)
        .operation(operation)
        .operationResponse(result.toString())
        .amount(operation.getCost())
        .userBalance(userBalance)
        .build();

    return recordRepository.save(record);
  }

  private void validateUserBalance(BigDecimal userBalance, Operation operation) {
    if (userBalance.compareTo(operation.getCost()) < 0) {
      throw new InsifficientBalanceException();
    }
  }

}
