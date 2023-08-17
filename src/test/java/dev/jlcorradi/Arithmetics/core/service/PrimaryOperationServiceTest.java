package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.repository.operationRepository;
import dev.jlcorradi.Arithmetics.core.repository.RecordRepository;
import dev.jlcorradi.Arithmetics.core.executors.OperationExecutionManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
class PrimaryOperationServiceTest {

  @InjectMocks
  PrimaryOperationService subject;

  @Mock
  final operationRepository repository;
  @Mock
  final RecordRepository recordRepository;
  @Mock
  final OperationExecutionManager operationExecutionManager;
  @Mock
  final ArithmeticsUserService userService;

  @BeforeEach
  void setup() {

  }

  @Test
  void executeAdditionSuccess() {

  }
}