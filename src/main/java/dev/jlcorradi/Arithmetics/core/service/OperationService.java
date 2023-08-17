package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.base.CrudService;
import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.repository.OperationRepository;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.model.Operation;
import dev.jlcorradi.Arithmetics.core.model.Record;

public interface OperationService extends CrudService<Operation, Long, OperationRepository> {
  Record execute(ArithmeticsUser user, OperationType operationType, Object[] params);
}
