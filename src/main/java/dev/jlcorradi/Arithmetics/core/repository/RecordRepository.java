package dev.jlcorradi.Arithmetics.core.repository;

import dev.jlcorradi.Arithmetics.core.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
