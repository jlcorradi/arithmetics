package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.executors.OperationExecutionManager;
import dev.jlcorradi.Arithmetics.core.executors.OperationExecutor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

@RequiredArgsConstructor
@ExtendWith(MockitoExtension.class)
@ComponentScan(basePackages = "dev.jlcorradi.Arithmetics.core.executors")
class PrimaryOperationServiceTest {

  @Autowired
  OperationExecutionManager operationExecutionManager;

  @Test
  void executeAdditionSuccess() {

  }
}