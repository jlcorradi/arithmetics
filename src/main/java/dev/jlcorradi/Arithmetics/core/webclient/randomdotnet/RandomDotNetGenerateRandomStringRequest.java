package dev.jlcorradi.Arithmetics.core.webclient.randomdotnet;

import java.util.Map;

public record RandomDotNetGenerateRandomStringRequest(
    String jsonrpc,
    String method,
    Map<String, Object> params,
    Long id
) {
}
  