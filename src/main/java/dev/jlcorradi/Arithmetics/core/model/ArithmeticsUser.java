package dev.jlcorradi.Arithmetics.core.model;

import dev.jlcorradi.Arithmetics.core.base.SoftDeletable;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@Table(name = "arithmetics_user")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class ArithmeticsUser implements SoftDeletable {
  public static final String USER_AUTHORITY = "USER";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  @Builder.Default
  private RecordStatus status = RecordStatus.ACTIVE;

  @Column(name = "user_balance")
  private BigDecimal userBalance;
}
