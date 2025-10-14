package productivity.user_service.service;

import productivity.user_service.dto.UserRequest;
import productivity.user_service.dto.UserResponse;

public interface UserService {
  void registerUser(UserRequest request);

  UserResponse checkLoginUser(UserRequest request);
}
