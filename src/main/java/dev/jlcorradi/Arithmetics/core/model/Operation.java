package dev.jlcorradi.Arithmetics.core.model;

import dev.jlcorradi.Arithmetics.core.base.SoftDeletable;
import dev.jlcorradi.Arithmetics.core.commons.OperationType;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "operation")
public class Operation implements SoftDeletable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  private OperationType type;

  private BigDecimal cost;

  private RecordStatus status;
}
