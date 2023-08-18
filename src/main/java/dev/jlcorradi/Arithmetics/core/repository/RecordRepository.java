package dev.jlcorradi.Arithmetics.core.repository;

import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.model.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface RecordRepository extends JpaRepository<Record, Long> {
  Page<Record> findByUserAndDateBetween(
      Pageable pageable,
      ArithmeticsUser user,
      LocalDate initDate,
      LocalDate endDate
  );
}
