package dev.jlcorradi.Arithmetics.web.security;

import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.service.ArithmeticsUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ArithmeticsAuthenticationManager implements AuthenticationManager {

  private final ArithmeticsUserService userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String username = String.valueOf(authentication.getPrincipal());
    log.debug("Executing authentication with username and password - {}", username);
    return userService.findByUsername(username)
        .map(arithmeticsUser -> {
          if (passwordEncoder.matches(authentication.getCredentials().toString(), arithmeticsUser.getPassword())) {
            return new JwtAuthenticationToken(arithmeticsUser);
          }
          return null;
        }).orElseThrow(() -> new BadCredentialsException(MessageConstants.BAD_CREDENTIALS_ERR));
  }

}
