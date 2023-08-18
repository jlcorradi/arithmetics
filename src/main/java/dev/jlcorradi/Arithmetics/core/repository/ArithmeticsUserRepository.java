package dev.jlcorradi.Arithmetics.core.repository;

import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.Optional;

public interface ArithmeticsUserRepository extends JpaRepository<ArithmeticsUser, Long> {

  @Modifying
  @Query(value = "update arithmetics_user set user_balance=:amount where id = :userId", nativeQuery = true)
  void updateUserBalance(@Param("userId") Long userId, @Param("amount") BigDecimal amount);

  Optional<ArithmeticsUser> findOneByEmail(String username);
}
