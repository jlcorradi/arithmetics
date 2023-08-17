package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.base.CrudService;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.repository.ArithmeticsUserRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface ArithmeticsUserService extends CrudService<ArithmeticsUser, Long, ArithmeticsUserRepository> {
  Optional<ArithmeticsUser> findByUsername(String username);

  void updateBalance(ArithmeticsUser user, BigDecimal amount);
}
