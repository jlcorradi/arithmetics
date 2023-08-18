package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.repository.ArithmeticsUserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PrimaryArithmeticsUserService implements ArithmeticsUserService {

  @Getter
  private final ArithmeticsUserRepository repository;

  @Override
  public Optional<ArithmeticsUser> findByUsername(String username) {
    return repository.findOneByEmail(username);
  }

  @Transactional
  @Override
  public void updateBalance(ArithmeticsUser user, BigDecimal amount) {
    repository.updateUserBalance(user.getId(), amount);
  }

}
