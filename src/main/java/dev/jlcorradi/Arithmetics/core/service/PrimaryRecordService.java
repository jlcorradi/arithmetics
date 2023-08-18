package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.model.Record;
import dev.jlcorradi.Arithmetics.core.repository.RecordRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Data
@RequiredArgsConstructor
public class PrimaryRecordService implements RecordService {
  private final RecordRepository repository;

  @Override
  public Page<Record> queryRecords(
      Pageable pageable,
      ArithmeticsUser user,
      LocalDate initDate,
      LocalDate endDate) {
    return repository.findByUserAndDateBetween(pageable, user, initDate, endDate);
  }
}
