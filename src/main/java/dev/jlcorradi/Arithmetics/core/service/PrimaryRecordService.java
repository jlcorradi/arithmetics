package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.model.Record;
import dev.jlcorradi.Arithmetics.core.repository.RecordRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Data
@RequiredArgsConstructor
public class PrimaryRecordService implements RecordService {
  private final RecordRepository repository;

  @Override
  public Page<Record> queryRecords(
      Pageable pageable,
      ArithmeticsUser user,
      LocalDateTime initDate,
      LocalDateTime endDate,
      String description) {
    return repository.findByUserAndDateBetweenAndDescriptionContainsAndStatus(
        pageable,
        user,
        initDate.truncatedTo(ChronoUnit.DAYS),
        endDate.truncatedTo(ChronoUnit.DAYS),
        description,
        RecordStatus.ACTIVE
    );
  }
}
