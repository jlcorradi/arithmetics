package dev.jlcorradi.Arithmetics.core.model;

import dev.jlcorradi.Arithmetics.core.base.SoftDeletable;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name = "arithmetics_user")
public class ArithmeticsUser implements SoftDeletable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private RecordStatus status;
}
