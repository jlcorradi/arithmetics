package dev.jlcorradi.Arithmetics.web.security;

import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.service.ArithmeticsUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class ArithmeticsAuthenticationProvider implements AuthenticationProvider {

  private final ArithmeticsUserService userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = String.valueOf(authentication.getPrincipal());
    log.debug("Executing authentication with username and password - {}", username);

    Optional<ArithmeticsUser> byUsername = userService.findByUsername(username);
    if (byUsername.isPresent()) {
      ArithmeticsUser arithmeticsUser = byUsername.get();
      if (isValidUser(arithmeticsUser, String.valueOf(authentication.getCredentials()))) {
        return new JwtAuthenticationToken(arithmeticsUser);
      }
    }

    return null;
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
  }

  private boolean isValidUser(ArithmeticsUser user, String givenPassword) {
    return RecordStatus.ACTIVE.equals(user.getStatus()) &&
        passwordEncoder.matches(givenPassword, user.getPassword());
  }

}
