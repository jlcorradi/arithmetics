package dev.jlcorradi.Arithmetics.web.security;

import com.sun.security.auth.UserPrincipal;
import dev.jlcorradi.Arithmetics.web.Paths;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class JwtAuthControllerTest {

  @Mock
  AuthenticationManager authenticationManager;

  JwtAuthController authController;
  JwtTokenService jwtTokenService;
  MockMvc mockMvc;
  AuthenticationService authenticationService;

  @BeforeEach
  void setup() {
    jwtTokenService = new JwtTokenService();
    ReflectionTestUtils.setField(jwtTokenService, "jwtSecret", "s3cr3t");
    ReflectionTestUtils.setField(jwtTokenService, "accessTokenExpirationInMs", 3600);
    authenticationService = new AuthenticationService(authenticationManager, jwtTokenService);
    authController = new JwtAuthController(authenticationService);
    mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
  }

  @Test
  @DisplayName("Authentication Success")
  void shouldAuthenticateSuccessfully() throws Exception {

    // GIVEN
    when(authenticationManager.authenticate(any()))
        .thenReturn(new UsernamePasswordAuthenticationToken(new UserPrincipal("user@jlcorradi.dev"), null));

    // WHEN
    mockMvc.perform(
            post(Paths.API_AUTH_V1)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("dXNlckBqbGNvcnJhZGkucGVybW9kOnBhc3N3b3Jk"))
        .andExpect(status().is(200))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.access_token").exists());
  }

  @Test
  @DisplayName("Authentication Failed")
  void shouldAuthenticateFail() throws Exception {

    // GIVEN
    when(authenticationManager.authenticate(any()))
        .thenThrow(new BadCredentialsException("Bad Credentials"));

    // WHEN
    mockMvc.perform(
        post(Paths.API_AUTH_V1)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .content("dXNlckBqbGNvcnJhZGkucGVybW9kOnBhc3N3b3Jk")
    ).andExpect(status().is(401));
  }

}