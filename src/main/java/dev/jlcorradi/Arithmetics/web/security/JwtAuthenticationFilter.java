package dev.jlcorradi.Arithmetics.web.security;

import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.service.ArithmeticsUserService;
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
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  public static final String BEARER_ = "Bearer ";
  public static final String BEARER_PREFIX = "Bearer ";

  private final ArithmeticsUserService userService;
  private final JwtTokenService jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain)
      throws ServletException, IOException,
      AccountStatusException {
    // 1 - Decide
    String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    String jwt = StringUtils.hasText(authHeader) && authHeader.startsWith(BEARER_) ? authHeader.substring(7) : null;

    if (request.getServletPath().contains(Paths.API_AUTH_V1) ||
        authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
      log.debug("request not applicable for filter: {}", request.getRequestURI());
      filterChain.doFilter(request, response);
      return;
    }

    // 2 - Authenticate
    if (jwtProvider.isValidToken(jwt)) {
      String username = jwtProvider.getUsernameFromJWT(jwt);

      ArithmeticsUser arithmeticsUser = userService.findByUsername(username)
          .orElseThrow(() -> new BadCredentialsException(MessageConstants.ACCESS_DENIED_ERR));

      JwtAuthenticationToken auth = new JwtAuthenticationToken(arithmeticsUser);

      SecurityContext jwtAuthContext = SecurityContextHolder.createEmptyContext();
      jwtAuthContext.setAuthentication(auth);
      SecurityContextHolder.setContext(jwtAuthContext);

      log.debug("request authenticated by {}", getClass().getName());
    }

    // 3 - Next
    filterChain.doFilter(request, response);

    // 4 - No cleanup
  }
}
