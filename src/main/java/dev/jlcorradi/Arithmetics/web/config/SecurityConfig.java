package dev.jlcorradi.Arithmetics.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jlcorradi.Arithmetics.web.ErrorResponse;
import dev.jlcorradi.Arithmetics.web.Paths;
import dev.jlcorradi.Arithmetics.web.security.JwtFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  public static final String APPLICATION_JSON = "application/json";
  public static final String ACCESS_DENIED = "Access Denied";
  private final JwtFilter jwtFilter;
  private final ObjectMapper objectMapper;

  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(Paths.API_AUTH_V1).permitAll()
            .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
            .anyRequest().authenticated()
        )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exHandlingConfig ->
            exHandlingConfig
                .authenticationEntryPoint((request, response, authException) -> {
                  log.info(authException.getMessage());
                  response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                  response.setContentType(APPLICATION_JSON);
                  response
                      .getOutputStream()
                      .println(objectMapper.writeValueAsString(new ErrorResponse(ACCESS_DENIED)));
                })
        );

    log.info("You are using JWT Auth Custom configuration. Login is available at {}. " +
        "Credentials: username/password as x-www-form-urlencoded", Paths.API_AUTH_V1);

    return http.build();
  }
}
