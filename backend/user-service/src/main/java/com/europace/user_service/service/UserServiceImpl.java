package com.europace.user_service.service;

import com.europace.user_service.dto.RegisterRequest;
import com.europace.user_service.entity.User;
import com.europace.user_service.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(RegisterRequest request) throws BadRequestException {
        if (userRepository.existsByUsername(request.username()))
            throw new BadRequestException("Username is already taken");

        userRepository.save(new User(request.username(), request.password()));
    }
}
