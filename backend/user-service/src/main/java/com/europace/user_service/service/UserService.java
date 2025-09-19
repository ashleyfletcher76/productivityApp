package com.europace.user_service.service;

import com.europace.user_service.dto.UserRequest;

public interface UserService {
    void registerUser(UserRequest request);

    String loginUser(UserRequest request);
}
