package dev.jlcorradi.Arithmetics.web.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
  public static LocalDate resolveDateOrDefault(String dateAsString, Supplier<LocalDate> defaultDateSupplier) {
    try {
      return LocalDate.parse(dateAsString, DateTimeFormatter.ISO_DATE_TIME);
    } catch (Exception ex) {
      log.debug("String could not be resolved as date: {}", dateAsString);
    }
    return defaultDateSupplier.get();
  }
}
