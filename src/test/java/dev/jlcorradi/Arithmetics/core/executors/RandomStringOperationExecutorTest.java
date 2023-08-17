package dev.jlcorradi.Arithmetics.core.executors;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RandomStringOperationExecutorTest {

  WireMockServer wireMockServer = new WireMockServer(8079);

  @Autowired
  RandomStringOperationExecutor subject;

  @BeforeEach
  void seup() {
    wireMockServer.start();
  }

  @AfterEach
  void destroy() {
    wireMockServer.stop();
  }

  @Test
  void shouldGetRandomStringSuccessfully() {
    // GIVEN
    String mockedResponse = "{\"jsonrpc\":\"2.0\",\"result\":{\"random\":{\"data\":[\"gwsnmiahqrscmlgddfzymldlvkltwinr\"]," +
        "\"completionTime\":\"2023-08-17 13:44:57Z\"},\"bitsUsed\":150,\"bitsLeft\":249502,\"requestsLeft\":997," +
        "\"advisoryDelay\":2530},\"id\":1}";

    wireMockServer.stubFor(post(urlEqualTo("/json-rpc/2/invoke"))
        .willReturn(aResponse()
            .withStatus(HttpStatus.OK.value())
            .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .withBody(mockedResponse)));

    // WHEN
    String result = (String) subject.execute(new Object[]{32});

    // THEN
    assertEquals("gwsnmiahqrscmlgddfzymldlvkltwinr", result);
  }

}