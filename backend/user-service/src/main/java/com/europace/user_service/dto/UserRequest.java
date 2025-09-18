package com.europace.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank
        @Size(min = 3, max = 10)
        String username,
        @NotBlank
        @Size(min = 2, max = 20)
        String password
) { }
