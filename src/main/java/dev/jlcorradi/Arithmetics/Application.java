package dev.jlcorradi.Arithmetics;

import dev.jlcorradi.Arithmetics.core.commons.RecordStatus;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.service.PrimaryArithmeticsUserService;
import dev.jlcorradi.Arithmetics.web.config.DemoUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public CommandLineRunner init(DemoUser demoUser, PrimaryArithmeticsUserService arithmeticsUserService,
                                PasswordEncoder passwordEncoder) {
    return args -> {
      if (null == demoUser) {
        return;
      }

      String email = demoUser.getEmail();
      log.info("Creating demo user. Username: {} - password: {}", email, demoUser.getPassword());
      if (arithmeticsUserService.findByUsername(email).isEmpty()) {
        arithmeticsUserService.save(ArithmeticsUser.builder()
            .email(email)
            .password(passwordEncoder.encode(demoUser.getPassword()))
            .userBalance(demoUser.getBalance())
            .status(RecordStatus.ACTIVE)
            .build()
        );
      }
    };
  }

}
