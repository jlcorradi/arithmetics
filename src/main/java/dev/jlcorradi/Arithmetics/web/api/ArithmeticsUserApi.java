package dev.jlcorradi.Arithmetics.web.api;

import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.service.ArithmeticsUserService;
import dev.jlcorradi.Arithmetics.web.HeaderMessageType;
import dev.jlcorradi.Arithmetics.web.HttpUtils;
import dev.jlcorradi.Arithmetics.web.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static dev.jlcorradi.Arithmetics.web.HttpUtils.getLoggedinUser;

@RequiredArgsConstructor
@RestController
@RequestMapping(Paths.USER_V1)
public class ArithmeticsUserApi {

  private final ArithmeticsUserService userService;

  public record AddBalanceRequest(BigDecimal amount) {
  }

  @PutMapping("/balance")
  void addBalance(@RequestBody AddBalanceRequest request) {
    ArithmeticsUser loggedinUser = getLoggedinUser();
    BigDecimal newBalance = request.amount().add(loggedinUser.getUserBalance());
    userService.updateBalance(loggedinUser, newBalance);
    HttpUtils.addHeaderMessage(HeaderMessageType.SUCCESS, String.format(MessageConstants.BALANCE_ADDED_MSG, newBalance));
  }

  public record GetUserDataResponse(String email, BigDecimal userBalance) {
  }

  @GetMapping
  public ResponseEntity<GetUserDataResponse> getUserData() {
    ArithmeticsUser user = getLoggedinUser();
    return ResponseEntity.ok(
        new GetUserDataResponse(user.getEmail(), user.getUserBalance())
    );
  }

}
