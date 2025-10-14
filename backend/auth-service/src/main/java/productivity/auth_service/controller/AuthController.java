package productivity.auth_service.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import productivity.auth_service.service.TokenService;

@RestController
public class AuthController {

  private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  private final TokenService tokenService;
  private final RestClient restClient;

  public AuthController(TokenService tokenService, RestClient restClient) {
    this.tokenService = tokenService;
    this.restClient = restClient;
  }

  @GetMapping("/health")
  public String health() {
    return "Ok";
  }

  @GetMapping("/")
  public String checkName(Authentication auth) {
    return "Hello, " + auth.getName();
  }

  @PostMapping("/login")
  public ResponseEntity<LoginUserResponse> loginUser(
      @RequestBody LoginUserRequest loginUserRequest) {
    try {
      UserServiceResponse userServiceResponse =
          restClient
              .post()
              .uri("/check-login-user")
              .contentType(MediaType.APPLICATION_JSON)
              .body(loginUserRequest)
              .retrieve()
              .body(UserServiceResponse.class);

      String token = tokenService.generateToken(userServiceResponse);

      LoginUserResponse loginUserResponse =
          new LoginUserResponse(userServiceResponse.username, userServiceResponse.roles, token);

      return ResponseEntity.ok(loginUserResponse);

    } catch (HttpClientErrorException.Unauthorized e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token");
    } catch (RestClientResponseException e) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "User service unavailable");
    }
  }

  public record LoginUserRequest(String username, String password) {}

  public record UserServiceResponse(String username, String password, List<String> roles) {}

  public record LoginUserResponse(String username, List<String> roles, String token) {}
}
