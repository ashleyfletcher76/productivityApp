package com.europace.user_service.service;

import com.europace.user_service.dto.UserRequest;
import com.europace.user_service.entity.User;
import com.europace.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import com.europace.user_service.exception.BadRequestException;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserServiceImpl(
            UserRepository userRepository,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public void registerUser(UserRequest request) throws BadRequestException {
        if (userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("Username is already taken");
        }

        userRepository.save(new User(request.username(), request.password()));
    }

    @Override
    public String loginUser(UserRequest request) throws BadRequestException {
        if (!userRepository.existsByUsername(request.username())) {
            throw new BadRequestException("User cannot be found");
        }

        User user = userRepository.findByUsername(request.username());

        if (!request.password().equals(user.getPassword()))
            throw new BadRequestException("Password is incorrect. Try again!");

        return tokenService.issue(user.getUsername());
    }
}
