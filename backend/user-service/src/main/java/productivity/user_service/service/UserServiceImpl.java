package productivity.user_service.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import productivity.user_service.dto.UserRequest;
import productivity.user_service.dto.UserResponse;
import productivity.user_service.entity.User;
import productivity.user_service.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void registerUser(UserRequest request) {
    if (userRepository.existsByUsername(request.username()))
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Username is already taken");

    userRepository.save(new User(request.username(), request.password(), request.roles()));
  }

  @Override
  public UserResponse checkLoginUser(UserRequest request) {
    User user = userRepository.findByUsername(request.username());

    if (user == null || !request.password().equals(user.getPassword()))
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");

    return new UserResponse(user.getUsername(), user.getRoles());
  }
}
