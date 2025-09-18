package com.europace.user_service.service;

import com.europace.user_service.dto.UserRequest;
import com.europace.user_service.exception.BadRequestException;

public interface UserService {
    void registerUser(UserRequest request) throws BadRequestException;

    String loginUser(UserRequest request) throws BadRequestException;
}
