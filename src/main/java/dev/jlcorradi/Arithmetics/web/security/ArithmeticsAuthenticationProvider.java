package dev.jlcorradi.Arithmetics.web.security;

import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.service.ArithmeticsUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

    return userService.findByUsername(username)
        .filter(user -> isValidUser(user, String.valueOf(authentication.getCredentials())))
        .map(JwtAuthenticationToken::new)
        .orElseThrow(() -> new BadCredentialsException(MessageConstants.BAD_CREDENTIALS_ERR));
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
