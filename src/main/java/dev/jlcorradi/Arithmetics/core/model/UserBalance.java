package dev.jlcorradi.Arithmetics.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "user_balance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBalance {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "user_id")
  private ArithmeticsUser user;

  private BigDecimal amount;
}
