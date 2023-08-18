package dev.jlcorradi.Arithmetics.web.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Component
@ConfigurationProperties(prefix = "arithmetics.demo-user")
public class DemoUser {
  private String email;
  private String password;
  private BigDecimal balance;
}
