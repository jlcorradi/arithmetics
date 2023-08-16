package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.repository.ArithmeticsUserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@AllArgsConstructor
public class PrimaryArithmeticsUserService implements ArithmeticsUserService {

  @Getter
  private final ArithmeticsUserRepository repository;

  @Override
  public Optional<ArithmeticsUser> findByUsername(String username) {
    return repository.findOneByEmail(username);
  }

}
