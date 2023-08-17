package dev.jlcorradi.Arithmetics.core.dao;

import dev.jlcorradi.Arithmetics.core.model.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordDao extends JpaRepository<Record, Long> {
}
