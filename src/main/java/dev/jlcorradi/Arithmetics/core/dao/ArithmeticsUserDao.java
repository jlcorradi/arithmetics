package dev.jlcorradi.Arithmetics.core.dao;

import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArithmeticsUserDao extends JpaRepository<ArithmeticsUser, Long> {
  Optional<ArithmeticsUser> findOneByEmail(String username);
}
