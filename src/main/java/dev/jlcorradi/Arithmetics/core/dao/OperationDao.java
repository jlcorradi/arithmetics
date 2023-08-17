package dev.jlcorradi.Arithmetics.core.dao;

import dev.jlcorradi.Arithmetics.core.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationDao extends JpaRepository<Operation, Long> {
}
