package dev.jlcorradi.Arithmetics.core.service;

import dev.jlcorradi.Arithmetics.core.base.CrudService;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.model.Record;
import dev.jlcorradi.Arithmetics.core.repository.RecordRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface RecordService extends CrudService<Record, Long, RecordRepository> {
  Page<Record> queryRecords(
      Pageable pageable,
      ArithmeticsUser user,
      LocalDate initDate,
      LocalDate endDate);
}
