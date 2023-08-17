package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.dao.ArithmeticsUserDao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class PrimaryArithmeticsUserService implements ArithmeticsUserService {

  @Getter
  private final ArithmeticsUserDao repository;

  @Override
  public Optional<ArithmeticsUser> findByUsername(String username) {
    return repository.findOneByEmail(username);
  }

}
