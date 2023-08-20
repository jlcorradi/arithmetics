package dev.jlcorradi.Arithmetics.core.repository;

import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.model.Record;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface RecordRepository extends JpaRepository<Record, Long> {
  Page<Record> findByUserAndDateBetweenAndDescriptionContainsAndStatus(
      Pageable pageable,
      ArithmeticsUser user,
      LocalDateTime initDate,
      LocalDateTime endDate,
      String description,
      RecordStatus status);
}
