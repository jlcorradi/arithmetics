package dev.jlcorradi.Arithmetics.web;

import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

  public static HttpServletResponse getHttpServletResponse() {
    HttpServletResponse response =
        ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
            .getResponse();
    assert response != null;
    return response;
  }

  public static void addHeaderMessage(HeaderMessageType messageType, String message) {
    HttpServletResponse response = HttpUtils.getHttpServletResponse();
    response.addHeader(messageType.getHeaderName(), message);
  }

  public static Optional<ArithmeticsUser> getLoggedinUser() {
    return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getDetails())
        .map(o -> (ArithmeticsUser) o);
  }
}
