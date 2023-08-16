package dev.jlcorradi.Arithmetics.web.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationService {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenService tokenProvider;

  public String authenticate(String username, String password) {
    Authentication authenticate = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );
    SecurityContextHolder.getContext().setAuthentication(authenticate);

    String accessToken = tokenProvider.generateToken(username);

    return accessToken;
  }
}
