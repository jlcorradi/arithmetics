package dev.jlcorradi.Arithmetics.web.security;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class JwtAuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenService tokenProvider;

  @Setter
  private PasswordEncoder passwordEncoder;

  record LoginResponse(String access_token) {
  }

  @PostMapping(consumes = "application/x-www-form-urlencoded")
  public ResponseEntity<LoginResponse> login(String username, String password) {

    Authentication authenticate = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, password)
    );
    SecurityContextHolder.getContext().setAuthentication(authenticate);

    String accessToken = tokenProvider.generateToken(username);
    var response = new LoginResponse(accessToken);
    return ResponseEntity.ok(response);
  }
}