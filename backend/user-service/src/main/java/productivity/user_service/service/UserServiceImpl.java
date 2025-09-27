package productivity.user_service.service;

import productivity.user_service.dto.UserRequest;
import productivity.user_service.entity.User;
import productivity.user_service.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public void registerUser(UserRequest request) {
        if (userRepository.existsByUsername(request.username()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");

        userRepository.save(new User(request.username(), request.password()));
    }

    @Override
    public String loginUser(UserRequest request) {
        User user = userRepository.findByUsername(request.username());

        if (user == null || !request.password().equals(user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");

        return tokenService.issue(user.getUsername());
    }
}
