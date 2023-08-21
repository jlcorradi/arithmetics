package dev.jlcorradi.Arithmetics.web.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenService {

  @Value("${arithmetics.security.jwtSecret}")
  private String jwtSecret;

  @Value("${arithmetics.security.jwtAccessTokenExpirationInMs}")
  private int accessTokenExpirationInMs;

  public String getUsernameFromJWT(String token) {

    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();
    return claims.getSubject();
  }

  public boolean isValidToken(String authToken) {
    if (!StringUtils.hasText(authToken)) {
      return false;
    }

    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (JwtException ex) {
      log.warn(ex.getMessage());
    }

    return false;
  }

  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpirationInMs))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }
}