package dev.jlcorradi.Arithmetics.web.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static dev.jlcorradi.Arithmetics.web.Paths.API_AUTH_V1;

@Slf4j
@RestController
@RequestMapping(API_AUTH_V1)
@AllArgsConstructor
public class JwtAuthController {

  private final AuthenticationService authenticationService;

  record LoginResponse(String access_token) {
  }

  @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  public ResponseEntity<LoginResponse> login(String username, String password) {
    LoginResponse response = null;
    try {
      response = new LoginResponse(authenticationService.authenticate(username, password));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    return ResponseEntity.ok(response);
  }
}