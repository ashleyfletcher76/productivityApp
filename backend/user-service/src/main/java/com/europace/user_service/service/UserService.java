package com.europace.user_service.service;

import com.europace.user_service.dto.RegisterRequest;
import org.apache.coyote.BadRequestException;

public interface UserService {
    void registerUser(RegisterRequest request) throws BadRequestException;
}
