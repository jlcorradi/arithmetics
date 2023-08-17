package dev.jlcorradi.Arithmetics.core.model;

import dev.jlcorradi.Arithmetics.core.base.SoftDeletable;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@Table(name = "arithmetics_user")
@NoArgsConstructor
@AllArgsConstructor
public class ArithmeticsUser implements SoftDeletable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;

  private String password;

  @Enumerated(EnumType.STRING)
  private RecordStatus status;
}
