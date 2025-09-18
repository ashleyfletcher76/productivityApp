package com.europace.user_service.controller;

import com.europace.user_service.dto.RegisterRequest;
import com.europace.user_service.service.UserService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String health() { return "Ok"; }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterRequest registerRequest) throws BadRequestException {
        userService.registerUser(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("User register successfully");
    }
}
