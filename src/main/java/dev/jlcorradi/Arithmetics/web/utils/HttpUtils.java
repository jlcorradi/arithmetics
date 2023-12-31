package dev.jlcorradi.Arithmetics.web.utils;

import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.web.HeaderMessageType;
import dev.jlcorradi.Arithmetics.web.security.JwtAuthenticationToken;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * Utility class for working with HTTP-related tasks.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpUtils {

  /**
   * Gets the current HttpServletResponse associated with the request.
   *
   * @return The current HttpServletResponse instance.
   * @throws IllegalStateException If there is no active request.
   */
  public static HttpServletResponse getHttpServletResponse() {
    HttpServletResponse response =
        ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
            .getResponse();
    assert response != null;
    return response;
  }

  /**
   * Adds a header message to the HttpServletResponse.
   *
   * @param messageType The type of the header message.
   * @param message     The message content to be added.
   */
  public static void addHeaderMessage(HeaderMessageType messageType, String message) {
    HttpServletResponse response = HttpUtils.getHttpServletResponse();
    response.addHeader(messageType.getHeaderName(), message);
  }

  /**
   * Retrieves the currently logged-in user details from the SecurityContextHolder.
   *
   * @return An Optional containing the logged-in user's details if available,
   * or an empty Optional if not logged in or details are not available.
   */
  public static ArithmeticsUser getLoggedinUser() {
    if (!JwtAuthenticationToken.class.isAssignableFrom(
        SecurityContextHolder.getContext().getAuthentication().getClass())) {
      return null;
    }
    return (ArithmeticsUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}

