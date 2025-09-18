package com.europace.user_service.controller;

import com.europace.user_service.dto.UserRequest;
import com.europace.user_service.service.TokenService;
import com.europace.user_service.service.UserService;
import jakarta.validation.Valid;
import com.europace.user_service.exception.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;

    public UserController(
            UserService userService,
            TokenService tokenService
    ) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @GetMapping("/health")
    public String health() { return "Ok"; }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRequest userRequest) throws BadRequestException {
        try {
            userService.registerUser(userRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body("User register successfully");
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad Request for user registration");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@Valid @RequestBody UserRequest userRequest) throws BadRequestException {
        try {
            String token = userService.loginUser(userRequest);
            return ResponseEntity.ok(new TokenResponse(token));
        } catch (BadRequestException e) {
            throw new BadRequestException("Bad Request for user log in");
        }
    }

    @PostMapping("/token")
    public ResponseEntity<TokenVerifyResponse> verifyToken(@RequestBody TokenRequest request) {
        return tokenService.verify(request.token())
                .map(user -> ResponseEntity.ok(new TokenVerifyResponse(true, user)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new TokenVerifyResponse(false, null)));
    }

    record TokenResponse(String token) {}
    record TokenRequest(String token) {}
    record TokenVerifyResponse(boolean valid, String username) {}
}
