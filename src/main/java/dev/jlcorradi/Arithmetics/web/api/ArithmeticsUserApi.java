package dev.jlcorradi.Arithmetics.web.api;

import dev.jlcorradi.Arithmetics.core.BusinessException;
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

@RequiredArgsConstructor
@RestController
@RequestMapping(Paths.USER_V1)
public class ArithmeticsUserApi {

  private final ArithmeticsUserService userService;

  public record AddBalanceRequest(BigDecimal amount) {
  }

  @PutMapping("/balance")
  void addBalance(@RequestBody AddBalanceRequest request) {
    ArithmeticsUser loggedinUser = getUser();
    BigDecimal newBalance = request.amount().add(loggedinUser.getUserBalance());
    userService.updateBalance(loggedinUser, newBalance);
    HttpUtils.addHeaderMessage(HeaderMessageType.SUCCESS, String.format(MessageConstants.BALANCE_ADDED_MSG, newBalance));
  }

  public record GetUserDataResponse(String email, BigDecimal userBalance) {
  }

  @GetMapping
  public ResponseEntity<GetUserDataResponse> getUserData() {
    ArithmeticsUser user = getUser();
    return ResponseEntity.ok(
        new GetUserDataResponse(user.getEmail(), user.getUserBalance())
    );
  }

  private static ArithmeticsUser getUser() {
    ArithmeticsUser loggedinUser = HttpUtils.getLoggedinUser()
        .orElseThrow(() -> new BusinessException(MessageConstants.ACCESS_DENIED_ERR));
    return loggedinUser;
  }

}
