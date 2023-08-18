package dev.jlcorradi.Arithmetics.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationService {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenService tokenProvider;

  public String authenticate(String username, String password) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );

    return tokenProvider.generateToken(username);
  }
}
