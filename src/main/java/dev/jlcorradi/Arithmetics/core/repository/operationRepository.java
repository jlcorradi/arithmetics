package dev.jlcorradi.Arithmetics.core.repository;

import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import dev.jlcorradi.Arithmetics.core.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface operationRepository extends JpaRepository<Operation, Long> {
  Optional<Operation> findOneByTypeAndStatus(OperationType type, RecordStatus status);
}