package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.BusinessException;
import dev.jlcorradi.Arithmetics.core.InsifficientBalanceException;
import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import dev.jlcorradi.Arithmetics.core.executors.*;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.model.Record;
import dev.jlcorradi.Arithmetics.core.repository.ArithmeticsUserRepository;
import dev.jlcorradi.Arithmetics.core.repository.OperationRepository;
import dev.jlcorradi.Arithmetics.core.repository.RecordRepository;
import dev.jlcorradi.Arithmetics.core.webclient.randomdotnet.RandomOrgClient;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static dev.jlcorradi.Arithmetics.utils.DummyObjectGenerator.operation;
import static dev.jlcorradi.Arithmetics.utils.DummyObjectGenerator.user;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class PrimaryOperationServiceTest {

  PrimaryOperationService subject;

  @Mock
  OperationRepository operationRepository;
  @Mock
  RecordRepository recordRepository;
  @Mock
  ArithmeticsUserRepository userRepository;
  @Mock
  RandomOrgClient randomClientFeign;

  ArithmeticsUserService userService;
  OperationExecutionManager operationExecutionManager;

  @BeforeEach
  void setup() {
    operationExecutionManager = new OperationExecutionManager(List.of(
        new AdditionOperationExecutor(),
        new SubtractionOperationExecutor(),
        new MultiplicationOperationExecutor(),
        new DivisionOperationExecutor(),
        new SquareRootOperationExecutor(),
        new RandomStringOperationExecutor(randomClientFeign)
    ));

    userService = new PrimaryArithmeticsUserService(userRepository);

    subject = new PrimaryOperationService(operationRepository, recordRepository, operationExecutionManager, userService);

    lenient().when(recordRepository.save(any(Record.class)))
        .thenAnswer((Answer<Record>) invocation -> (Record) invocation.getRawArguments()[0]);
  }

  @Test
  @DisplayName("Execute Operation Successfully")
  void executeOperationSuccess() {
    // GIVEN
    ArithmeticsUser user = user(x -> x.userBalance(BigDecimal.TEN));

    when(operationRepository.findOneByTypeAndStatus(OperationType.ADDITION, RecordStatus.ACTIVE))
        .thenReturn(Optional.ofNullable(operation(x -> x.type(OperationType.ADDITION).cost(BigDecimal.ONE))));

    // WHEN
    Record result = subject.execute(user, OperationType.ADDITION, new Object[]{10D, 5D});

    // THEN
    verify(userRepository, times(1)).updateUserBalance(1L, result.getUserBalance());
    assertEquals(0, BigDecimal.valueOf(9D).compareTo(result.getUserBalance()));
    assertEquals(0, BigDecimal.valueOf(1D).compareTo(result.getAmount()));
    assertEquals("15.0", result.getOperationResponse());
  }

  @Test
  @DisplayName("Fail Random String and Keep user balance")
  void failRandomStringKeepUserBalance() {
    // GIVEN
    ArithmeticsUser user = user(x -> x.userBalance(BigDecimal.TEN));

    when(operationRepository.findOneByTypeAndStatus(OperationType.RANDOM_STRING, RecordStatus.ACTIVE))
        .thenReturn(Optional.ofNullable(operation(x -> x.type(OperationType.ADDITION).cost(BigDecimal.ONE))));

    when(randomClientFeign.generateRandomString(any()))
        .thenThrow(new RuntimeException());

    // WHEN
    BusinessException ex = assertThrows(BusinessException.class,
        () -> subject.execute(user, OperationType.RANDOM_STRING, new Object[]{5D})
    );

    // THEN
    assertEquals(ex.getMessage(), MessageConstants.RETRIEVING_RANDOM_STRING_ERR);
    verify(userRepository, times(0)).updateUserBalance(any(), any());
  }

  @Test
  @DisplayName("Fail due to Insufficient balance")
  void failInsufficientBalance() {
    // GIVEN
    ArithmeticsUser user = user(x -> x.userBalance(BigDecimal.ONE));

    when(operationRepository.findOneByTypeAndStatus(OperationType.ADDITION, RecordStatus.ACTIVE))
        .thenReturn(Optional.ofNullable(operation(x -> x.type(OperationType.ADDITION).cost(BigDecimal.ONE))));

    // WHEN
    InsifficientBalanceException ex = assertThrows(InsifficientBalanceException.class, () -> subject.execute(user, OperationType.ADDITION, new Object[]{10D, 5D}));

    // THEN
    verify(userRepository, times(0)).updateUserBalance(any(), any());
    assertEquals(MessageConstants.INSUFFICIENT_BALANCE_ERR, ex.getMessage());
  }
}