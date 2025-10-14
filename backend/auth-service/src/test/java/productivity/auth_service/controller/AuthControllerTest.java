package productivity.auth_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import productivity.auth_service.config.AuthClientConfig;
import productivity.auth_service.controller.AuthController.LoginUserRequest;
import productivity.auth_service.controller.AuthController.UserServiceResponse;
import productivity.auth_service.service.TokenService;

@WebMvcTest(controllers = AuthController.class)
@Import(AuthClientConfig.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

  private static MockWebServer mockWebServer;

  @BeforeAll
  static void startServer() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();
  }

  @AfterAll
  static void stopServer() throws IOException {
    mockWebServer.shutdown();
  }

  // Point user.service.base-url to the mock server
  @DynamicPropertySource
  static void registerBaseUrl(DynamicPropertyRegistry registry) {
    registry.add("user.service.base-url", () -> mockWebServer.url("/").toString());
  }

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private TokenService tokenService;

  @Test
  void login_happyPath_returns200WithToken() throws Exception {
    var userResponse = new UserServiceResponse("alice", "ignored", List.of("USER"));
    mockWebServer.enqueue(
        new MockResponse()
            .setResponseCode(200)
            .addHeader("Content-Type", "application/json")
            .setBody(objectMapper.writeValueAsString(userResponse)));

    when(tokenService.generateToken(any(UserServiceResponse.class))).thenReturn("TEST_TOKEN");

    var requestJson = objectMapper.writeValueAsString(new LoginUserRequest("alice", "secret"));

    mockMvc
        .perform(
            post("/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.username").value("alice"))
        .andExpect(jsonPath("$.roles[0]").value("USER"))
        .andExpect(jsonPath("$.token").value("TEST_TOKEN"));
  }

  @Test
  void login_userServiceReturns401_mapsTo401InvalidToken() throws Exception {
    mockWebServer.enqueue(new MockResponse().setResponseCode(401));

    var requestJson = objectMapper.writeValueAsString(new LoginUserRequest("alice", "wrong"));

    mockMvc
        .perform(
            post("/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isUnauthorized())
        .andExpect(status().reason("Invalid token"));
  }

  @Test
  void login_userServiceReturns5xx_mapsTo503ServiceUnavailable() throws Exception {
    mockWebServer.enqueue(new MockResponse().setResponseCode(503));

    var requestJson = objectMapper.writeValueAsString(new LoginUserRequest("alice", "secret"));

    mockMvc
        .perform(
            post("/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
        .andExpect(status().isServiceUnavailable())
        .andExpect(status().reason("User service unavailable"));
  }
}
