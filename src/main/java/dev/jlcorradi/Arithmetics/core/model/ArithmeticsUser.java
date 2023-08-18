package dev.jlcorradi.Arithmetics.core.model;

import dev.jlcorradi.Arithmetics.core.base.SoftDeletable;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.Collection;

@Data
@Builder
@Entity
@Table(name = "arithmetics_user")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "password")
public class ArithmeticsUser implements SoftDeletable, UserDetails {
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

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return AuthorityUtils.commaSeparatedStringToAuthorityList(USER_AUTHORITY);
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return RecordStatus.ACTIVE == status;
  }

  @Override
  public boolean isAccountNonLocked() {
    return RecordStatus.ACTIVE == status;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return RecordStatus.ACTIVE == status;
  }

  @Override
  public boolean isEnabled() {
    return RecordStatus.ACTIVE == status;
  }
}
