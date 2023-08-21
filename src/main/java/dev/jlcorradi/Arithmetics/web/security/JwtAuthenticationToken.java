package dev.jlcorradi.Arithmetics.web.security;

import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

  private final ArithmeticsUser principal;

  public JwtAuthenticationToken(ArithmeticsUser principal) {
    super(AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
    this.principal = principal;
    this.setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return null;
  }

  @Override
  public ArithmeticsUser getPrincipal() {
    return this.principal;
  }
}
