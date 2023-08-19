package dev.jlcorradi.Arithmetics.core.model;

import dev.jlcorradi.Arithmetics.core.base.SoftDeletable;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "record")
public class Record implements SoftDeletable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "operation_id")
  private Operation operation;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private ArithmeticsUser user;

  private String description;

  private BigDecimal amount;

  @Column(name = "user_balance")
  private BigDecimal userBalance;

  @Column(name = "operation_response")
  private String operationResponse;

  private LocalDate date;

  @Builder.Default
  @Enumerated(EnumType.STRING)
  private RecordStatus status = RecordStatus.ACTIVE;

}
