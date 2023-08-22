package dev.jlcorradi.Arithmetics.web;

import dev.jlcorradi.Arithmetics.core.BusinessException;
import dev.jlcorradi.Arithmetics.core.MessageConstants;
import dev.jlcorradi.Arithmetics.web.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApiErrorHandler {

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<?> handleException(BusinessException ex) {
    if (log.isDebugEnabled()) {
      log.debug("Handling Business: {}", ex.getMessage());
    }
    HttpUtils.addHeaderMessage(HeaderMessageType.ERROR, ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ex.getMessage()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(Exception ex) {
    log.error("Handling Uncaught:", ex);
    HttpUtils.addHeaderMessage(HeaderMessageType.ERROR, ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(MessageConstants.GENERIC_EXECUTION_ERR));
  }
}
