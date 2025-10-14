package productivity.user_service.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import productivity.user_service.dto.UserRequest;
import productivity.user_service.dto.UserResponse;
import productivity.user_service.service.UserService;

@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/health")
  public String health() {
    return "Ok";
  }

  @PostMapping("/register")
  public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequest userRequest) {
    userService.registerUser(userRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body("User register successfully");
  }

  @PostMapping("/check-login-user")
  public ResponseEntity<UserResponse> loginUser(@Valid @RequestBody UserRequest userRequest) {
    UserResponse userResponse = userService.checkLoginUser(userRequest);
    return ResponseEntity.ok(userResponse);
  }
}
