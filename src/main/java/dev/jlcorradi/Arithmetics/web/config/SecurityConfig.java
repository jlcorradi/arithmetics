package dev.jlcorradi.Arithmetics.web.config;

import dev.jlcorradi.Arithmetics.web.Paths;
import dev.jlcorradi.Arithmetics.web.security.ArithmeticsAuthenticationProvider;
import dev.jlcorradi.Arithmetics.web.security.CustomAuthenticationEntryPointErrorHandler;
import dev.jlcorradi.Arithmetics.web.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;

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
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .exceptionHandling(exHandlingConfig ->
            exHandlingConfig.authenticationEntryPoint(new CustomAuthenticationEntryPointErrorHandler())
        );

    log.info("You are using JWT Auth Custom configuration. Login is available at {}. " +
        "Credentials: username/password as x-www-form-urlencoded", Paths.API_AUTH_V1);

    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      HttpSecurity http,
      ArithmeticsAuthenticationProvider authenticationProvider) throws Exception {
    return http.getSharedObject(AuthenticationManagerBuilder.class)
        .authenticationProvider(authenticationProvider)
        .build();
  }
}
