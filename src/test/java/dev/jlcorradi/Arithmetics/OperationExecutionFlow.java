package dev.jlcorradi.Arithmetics;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import dev.jlcorradi.Arithmetics.core.model.ArithmeticsUser;
import dev.jlcorradi.Arithmetics.core.service.ArithmeticsUserService;
import dev.jlcorradi.Arithmetics.web.Paths;
import dev.jlcorradi.Arithmetics.web.api.ExecutionsApi;
import dev.jlcorradi.Arithmetics.web.security.JwtAuthApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Map;

import static dev.jlcorradi.Arithmetics.utils.DummyObjectGenerator.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
public class OperationExecutionFlow {
  WireMockServer wireMockServer = new WireMockServer(8079);
  MockMvc mockMvc;

  @Autowired
  JwtAuthApi authApi;

  @Autowired
  ExecutionsApi executionsApi;

  @Autowired
  ArithmeticsUserService userService;

  @Autowired
  ObjectMapper mapper;


  @BeforeEach
  void seup() {
    wireMockServer.start();
    mockMvc = MockMvcBuilders.standaloneSetup(authApi, executionsApi).build();
  }

  @AfterEach
  void destroy() {
    wireMockServer.stop();
  }

  @Test
  void executeOperationFlowSuccess() throws Exception {
    ArithmeticsUser user = userService.save(user(x -> x.userBalance(BigDecimal.valueOf(20D))));
    // Loggin in
    MvcResult mvcResult = mockMvc.perform(
            post(Paths.API_AUTH_V1)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", user.getUsername())
                .param("password", user.getPassword()))
        .andExpect(status().is2xxSuccessful())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.access_token").exists())
        .andReturn();

    Map map = mapper.readValue(mvcResult.getResponse().getContentAsString(), Map.class);
    String token = (String) map.get("access_token");
    log.info(token);

  }
}
