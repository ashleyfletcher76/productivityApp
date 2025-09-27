package productivity.user_service.service;

import productivity.user_service.dto.UserRequest;

public interface UserService {
    void registerUser(UserRequest request);

    String loginUser(UserRequest request);
}
