package dev.jlcorradi.Arithmetics.web.security;

import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.web.Paths;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

  public static final String BEARER_ = "Bearer ";

  public final UserDetailsService userService;
  private final JwtTokenService jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException,
      AccountStatusException {
    try {
      if (request.getServletPath().contains(Paths.API_AUTH_V1)) {
        filterChain.doFilter(request, response);
        return;
      }

      String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
      String jwt = StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_) ? authHeader.substring(7) : null;

      if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }

      if (StringUtils.hasText(jwt) && jwtProvider.isValidToken(jwt)) {
        String username = jwtProvider.getUsernameFromJWT(jwt);

        UserDetails userDetails = userService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities()
            );

        authToken.setDetails(userDetails);
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    } catch (Exception ex) {
      throw new BadCredentialsException(MessageConstants.ACCESS_DENIED_ERR);
    }

    filterChain.doFilter(request, response);
  }
}
